package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;
import com.kagaya.kyaputen.core.dao.*;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.metrics.Monitor;
import com.kagaya.kyaputen.core.service.DecideService;
import com.kagaya.kyaputen.core.service.KubernetesService;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作流执行逻辑，为其他服务提供任务队列操作函数
 */
public class WorkflowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

    private DecideService decideService = new DecideService();

    private ExecutionDAO executionDAO = new ExecutionDAOImpl();

    private QueueDAO pollingQueue = new PollingQueueDAOImpl();

    private WorkflowQueue workflowQueue;

    private PodResourceDAO podResourceDAO = new PodResourceDAO();

    private KubernetesService k8s = new KubernetesService();

//    @Inject
//    public WorkflowExecutor(ExecutionDAO executionDAO, QueueDAO queueDAO) {
//        this.executionDAO = executionDAO;
//        this.pollingQueue = queueDAO;
//    }

    /**
     * 创建工作流，进入工作流队列，设置workflowId
     * @param workflowDef 工作流定义
     * @return workflow 工作流实例
     */
    public Workflow createWorkflow(WorkflowDefinition workflowDef, ExecutionPlan plan) {
        Workflow workflow = executionDAO.createWorkflow(workflowDef, plan);
        return workflow;
    }

    /**
     * 用新的执行计划更新已有工作流实例
     * @param workflow - 已有工作流对象
     * @param plan
     * @return
     */
    public Workflow updateWorkflow(Workflow workflow, ExecutionPlan plan) {

        List<String> tasks = workflow.getTaskNames();

        for (String tn: tasks) {
            Task t = workflow.getTask(tn);
            TaskExecutionPlan p = plan.getTaskExecutionPlan(t.getTaskDefName());
            t.setTaskId(p.getTaskId());
            t.setWorkerId(p.getPodId());
            t.setStartTime(0);
        }

        return workflow;
    }

    /**
     * 启动指定工作流，赋值输入参数
     * @param workflow 工作流实例
     */
    public void startWorkflow(Workflow workflow, Map<String, Object> inputParam) {
        workflow.setInput(inputParam);
        workflow.setStatus(WorkflowStatus.RUNNING);
        decide(workflow.getWorkflowId());
    }

    public void updateTask(TaskResult taskResult) {
        if (taskResult == null) {
            throw new ExecutionException(ExecutionException.Code.INVALID_INPUT, "TaskResult object is null");
        }

        String workflowId = taskResult.getWorkflowInstanceId();
        Workflow workflow = executionDAO.getWorkflow(workflowId);
        Task task = workflow.getTask(taskResult.getTaskId());
        String taskQueueName = QueueUtils.getQueueName(task);

        if (task.getStatus().isTerminal()) {
            // Task was already updated....
            pollingQueue.remove(taskQueueName, taskResult.getTaskId());
            logger.info("Task: {} - {} has already finished execution with status: {} within workflow: {} - {}. Removed task from queue: {}", task.getTaskDefName(), task.getTaskId(), task.getStatus(), workflow.getName(), task.getWorkflowInstanceId(), taskQueueName);
            return;
        }

        if (workflow.getStatus().isTerminal()) {
            // Workflow is in terminal state
            pollingQueue.remove(taskQueueName, taskResult.getTaskId());
            logger.info("Workflow: {} - {} has already finished execution. Task update for: {} ignored and removed from Queue: {}.", workflow.getName(), taskResult.getWorkflowInstanceId(), taskResult.getTaskId(), taskQueueName);
            return;
        }

        task.setStatus(Task.Status.COMPLETED);
        task.setWorkerId(taskResult.getWorkerId());
        task.setOutputData(taskResult.getOutputData());

        logger.debug("Task: {} being updated", task.getTaskId());

        decide(workflowId);

        if (task.getStatus().isTerminal()) {
            long duration = getTaskDuration(task);
            long latency = System.currentTimeMillis() - task.getEndTime();
            Monitor.logTaskExecutionTime(task.getTaskType(), duration);
            Monitor.logTaskLatencyTime(task, latency);
        }

        // 检查pod，判断是否需要伸缩
        WorkflowDefinition workflowDef = workflow.getWorkflowDefinition();
        Pod pod = podResourceDAO.getPod(task.getWorkerId());
        pod.finishTaskExecutionPlan(task.getTaskId());
        if (pod.needUpdate())
            k8s.resizePod(pod, workflowDef.getCeByType(task.getTaskType()));
    }

    public Task getTask(String workflowId, String taskId) {
        return executionDAO.getTask(workflowId, taskId);
    }

    /**
     * 推送任务到轮询队列
     * @param workflowId 工作流Id
     * @return true - 工作流执行结束  false - 工作流未结束
     */
    public boolean decide(String workflowId) {

        Workflow workflow = executionDAO.getWorkflow(workflowId);

        if (workflow.getStatus().isTerminal()) {
            return true;
        }

        try {
            // 决定需要执行的任务
            DecideService.DecideOutcome outcome = decideService.decide(workflow);
            if (outcome.isComplete) {
                completeWorkflow(workflow);
                return true;
            }

            // 就绪任务
            List<Task> tasksToBeScheduled = outcome.tasksToBeScheduled;
            // 正在执行待更新任务
            List<Task> tasksToBeUpdated = outcome.tasksToBeUpdated;

            // 赋值输入参数，加入对应任务轮询队列
            for (Task task : tasksToBeScheduled) {
                if (task.getWorkflowInstanceId().equals(workflowId)) {
                    task.setStatus(Task.Status.SCHEDULED);
                    populateTaskInputData(task);
                    TaskMessage message = new TaskMessage(task.getWorkflowInstanceId(), task.getTaskId(), task.getPriority(), task.getWorkerId());
                    String queueName = QueueUtils.getQueueName(task);
                    task.setScheduledTime(System.currentTimeMillis());

                    // 考虑直接添加到pod结构到执行队列中？
                    pollingQueue.push(queueName, message);
                }
            }


        } catch (Exception e) {
            logger.error("WorkflowExecutor decide error", e);
        }

        return false;
    }

    private long getTaskDuration(Task task) {
        return task.getEndTime() - task.getStartTime();
    }

    private void completeWorkflow(Workflow workflow) {

        // 若成功结束
        workflow.setStatus(WorkflowStatus.COMPLETED);
        workflow.setEndTime(System.currentTimeMillis());
        workflowQueue.remove(workflow.getWorkflowId());

        logger.info("Workflow: {} - {} completed successful.", workflow.getName(), workflow.getWorkflowId());
    }

    @Deprecated
    public void completeWorkflow(String workflowId) {
        Workflow wf = executionDAO.getWorkflow(workflowId);

        if (wf.getStatus().equals(WorkflowStatus.COMPLETED)) {
            logger.info("Workflow: {} - {} completed successful.", wf.getName(), workflowId);
            workflowQueue.remove(workflowId);
        }
    }

    /**
     * 填充输入参数
     * @param task
     */
    private void populateTaskInputData(Task task) {

        List<String> priorTasks = task.getTaskDefinition().getPriorTasks();
        Map<String, Object> inputData = new HashMap<>();

        Workflow workflow = executionDAO.getWorkflow(task.getWorkflowInstanceId());

        if (priorTasks.size() == 0) {
            // 第一个任务
            inputData.putAll(workflow.getInput());
        } else {
            for (String taskName: priorTasks) {
                Task t = workflow.getTask(taskName);
                inputData.putAll(t.getOutputData());
            }
        }

        task.setInputData(inputData);
    }

    public void pauseWorkflow(String workflowId) {

    }

    public void terminateWorkflow(String workflowId, String reason) {

    }

}
