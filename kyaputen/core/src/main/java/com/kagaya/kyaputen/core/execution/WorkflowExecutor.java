package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.ExecutionDAO;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.service.DeciderService;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class WorkflowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

    private final QueueDAO queueDAO;

    private final DeciderService deciderService;

    private final ExecutionDAO executionDAO;

    @Inject
    public WorkflowExecutor(QueueDAO queueDAO, ExecutionDAO executionDAO, DeciderService deciderService) {
        this.queueDAO = queueDAO;
        this.executionDAO = executionDAO;
        this.deciderService = deciderService;
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

        task.setStatus(Task.Status.SCHEDULED);
        task.setWorkerId(taskResult.getWorkerId());
        task.setOutputData(taskResult.getOutputData());

        logger.debug("Task: {} being updated", task.getTaskId());

//        if (task.getStatus().isTerminal()) {
//            task.setEndTime(System.currentTimeMillis());
//        }

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

    public boolean decide(String workflowId) {

        Workflow workflow = executionDAO.getWorkflow(workflowId);

        if (workflow.getStatus().isTerminal()) {
            return true;
        }

        try {
            // 决定需要执行的任务
            DeciderService.DeciderOutcome outcome = deciderService.decide(workflow);
            if (outcome.isComplete) {
                completeWorkflow(workflow);
                return true;
            }

            List<Task> tasksToBeScheduled = outcome.tasksToBeScheduled;
            List<Task> tasksToBeUpdated = outcome.tasksToBeUpdated;


        } catch (Exception e) {

        }
    }

    private long getTaskDuration(long s, Task task) {
        long duration = task.getEndTime() - task.getStartTime();

        return s + duration;
    }

    private void completeWorkflow(Workflow workflow) {

    }

}
