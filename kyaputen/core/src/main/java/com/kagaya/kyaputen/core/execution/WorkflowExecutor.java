package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
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
import java.util.List;

public class WorkflowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

    private final QueueDAO queueDAO;

    private final DecideService decideService;

    private final ExecutionDAO executionDAO;

    @Inject
    public WorkflowExecutor(QueueDAO queueDAO, ExecutionDAO executionDAO, DecideService decideService) {
        this.queueDAO = queueDAO;
        this.executionDAO = executionDAO;
        this.decideService = decideService;
    }

    public void startWorkflow() {

    }

    public void completeWorkflow() {

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
            queueDAO.remove(taskQueueName, taskResult.getTaskId());
            logger.info("Task: {} has already finished execution with status: {} within workflow: {}. Removed task from queue: {}", task.getTaskId(), task.getStatus(), task.getWorkflowInstanceId(), taskQueueName);
            return;
        }

        if (workflowInstance.getStatus().isTerminal()) {
            // Workflow is in terminal state
            queueDAO.remove(taskQueueName, taskResult.getTaskId());
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

    public Task getTask(String taskId) {
//        return getTaskById(taskId)
//                .map(task -> {
//                    if (task.getWorkflowTask() != null) {
//                        return metadataMapperService.populateTaskWithDefinition(task);
//                    }
//                    return task;
//                })
//                .orElse(null);

        return new Task();
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
                    queueDAO.push(workflowId, task.getTaskId(), MessageType.WorkflowMessage);
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
