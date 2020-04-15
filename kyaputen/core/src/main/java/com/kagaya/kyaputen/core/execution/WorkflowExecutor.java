package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.ExecutionDAO;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.dao.WorkflowQueue;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.service.DecideService;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import com.kagaya.kyaputen.core.metadata.WorkflowMetadata;
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

    private final DecideService decideService;

    private final ExecutionDAO executionDAO;

    /**
     * @description 工作流队列，按workflowInstanceId保存运行中的工作流任务
     */
    private WorkflowQueue workflowQueue;

    private QueueDAO<TaskMessage> pollingQueue;

    private WorkflowMetadata workflowDefs;

    @Inject
    public WorkflowExecutor(ExecutionDAO executionDAO, DecideService decideService) {
        this.executionDAO = executionDAO;
        this.decideService = decideService;
    }

    /**
     * 创建工作流，进入工作流队列，设置workflowId
     * @param workflowDef 工作流定义
     */
    public void createWorkflow(WorkflowDefinition workflowDef) {

        Workflow workflow = executionDAO.createWorkflow(workflowDef);

        // 调用资源分配算法

        // 向K8s申请资源


    }

    /**
     * 启动指定工作流，赋值输入参数
     * @param workflowName 工作流定义
     */
    public boolean startWorkflow(String workflowName, Map<String, Object> param) {

        List<Workflow> workflowList = workflowQueue.getByName(workflowName);

        if(workflowList.size() == 0) {
            logger.error("No such instance of workflow: " + workflowName);
            Workflow wf = executionDAO.createWorkflow(workflowDefs.get(workflowName));
            workflowList.add(wf);
        }

        // find a READY workflow instance
        for (Workflow wf: workflowList) {
            if (wf.getStatus().equals(WorkflowStatus.READY)) {
                wf.setInput(param);
                decide(wf.getWorkflowId());
                break;
            }
        }

        logger.error("No ready status instance of workflow: " + workflowName);
        return false;
    }

    public void completeWorkflow(String workflowId) {
        Workflow wf = executionDAO.getWorkflow(workflowId);

        if (wf.getStatus().equals(WorkflowStatus.COMPLETED)) {
            logger.info("Workflow: {} - {} completed successful.", wf.getName(), workflowId);
            workflowQueue.remove(workflowId);
        }
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
            long duration = getTaskDuration(0, task);
            long lastDuration = task.getEndTime() - task.getStartTime();
            // Monitors.recordTaskExecutionTime(task.getTaskDefName(), duration, true, task.getStatus());
            // Monitors.recordTaskExecutionTime(task.getTaskDefName(), lastDuration, false, task.getStatus());
        }
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
                    pollingQueue.push(queueName, message);
                }
            }


        } catch (Exception e) {
            logger.error("WorkflowExecutor decide error", e);
        }

        return false;
    }

    private long getTaskDuration(long s, Task task) {
        long duration = task.getEndTime() - task.getStartTime();

        return s + duration;
    }

    private void completeWorkflow(Workflow workflow) {

        // 若成功结束
        workflow.setStatus(WorkflowStatus.COMPLETED);

    }

    private void populateTaskInputData(Task task) {

        List<String> priorTasks = task.getTaskDefinition().getPriorTasks();
        Map<String, Object> inputData = new HashMap<>();

        Workflow workflow = executionDAO.getWorkflow(task.getWorkflowInstanceId());

        if (priorTasks.size() == 0) {
            // 第一个任务
            inputData.putAll(workflow.getInput());
        } else {
            List<Task> tasks = workflow.getTasks();

            for (Task t: tasks) {
                if (priorTasks.contains(t.getTaskDefName())) {
                    inputData.putAll(t.getOutputData());
                }
            }

        }

        task.setInputData(inputData);
    }

    public void pauseWorkflow(String workflowId) {

    }

    public void terminateWorkflow(String workflowId, String reason) {

    }

}
