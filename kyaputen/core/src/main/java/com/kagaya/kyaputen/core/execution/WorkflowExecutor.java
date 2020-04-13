package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.ExecutionDAO;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.events.Message.MessageType;
import com.kagaya.kyaputen.core.service.DecideService;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * @description 工作流执行逻辑，为其他服务提供任务队列操作函数
 */
public class WorkflowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

    private final QueueDAO<Task> taskQueue;

    private final DecideService decideService;

    private final ExecutionDAO executionDAO;

    /**
     * @description 工作流队列，按workflowInstanceId保存运行中的工作流任务
     */
    private final Map<String, Queue<Task>> workflowQueue;

    @Inject
    public WorkflowExecutor(QueueDAO<Task> taskQueue, ExecutionDAO executionDAO, DecideService decideService) {
        this.taskQueue = taskQueue;
        this.executionDAO = executionDAO;
        this.decideService = decideService;
        this.workflowQueue = new HashMap<>();
    }

    /**
     * @description 创建工作流，进入工作流队列，设置workflowId
     * @param workflowDef
     */
    public void createWorkflow(WorkflowDefinition workflowDef) {

    }

    /**
     * @description 启动指定工作流，赋值输入参数
     * @param workflowName
     */
    public void startWorkflow(String workflowName, Map<String, Object> param) {

    }

    public void completeWorkflow(String workflowId) {

    }


    public void updateTask(TaskResult taskResult) {
        if (taskResult == null) {
            throw new ExecutionException(ExecutionException.Code.INVALID_INPUT, "TaskResult object is null");
        }

        String workflowId = taskResult.getWorkflowInstanceId();
        Workflow workflowInstance = executionDAO.getWorkflow(workflowId);

//        if (workflowInstance.getWorkflowDefinition() == null) {
//            workflowInstance = metadataMapperService.populateWorkflowWithDefinitions(workflowInstance);
//        }

        Task task = executionDAO.getTask(taskResult.getTaskId());

        String taskQueueName = QueueUtils.getQueueName(task);

        if (task.getStatus().isTerminal()) {
            // Task was already updated....
            taskQueue.remove(taskQueueName, taskResult.getTaskId());
            logger.info("Task: {} has already finished execution with status: {} within workflow: {}. Removed task from queue: {}", task.getTaskId(), task.getStatus(), task.getWorkflowInstanceId(), taskQueueName);
            return;
        }

        if (workflowInstance.getStatus().isTerminal()) {
            // Workflow is in terminal state
            taskQueue.remove(taskQueueName, taskResult.getTaskId());
            logger.info("Workflow: {} has already finished execution. Task update for: {} ignored and removed from Queue: {}.", workflowInstance, taskResult.getTaskId(), taskQueueName);
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

    public Task getTask(String queueName, String taskId) {
        return taskQueue.get(queueName, taskId);
    }

    /**
     *
     * @param workflowId
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

            // 加入对应任务队列
            for (Task task : tasksToBeScheduled) {
                if (task.getWorkflowInstanceId() == workflowId) {
                    task.setStatus(Task.Status.SCHEDULED);
                    taskQueue.push(workflowId, task);
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

}
