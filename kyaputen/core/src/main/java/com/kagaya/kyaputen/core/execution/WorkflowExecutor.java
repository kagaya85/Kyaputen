package com.kagaya.kyaputen.core.execution;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.service.DeciderService;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WorkflowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

    private final QueueDAO queueDAO;

    private final DeciderService deciderService;

    @Inject
    public WorkflowExecutor(QueueDAO queueDAO, DeciderService deciderService) {
        this.queueDAO = queueDAO;
        this.deciderService = deciderService;
    }

    public void startWorkflow() {

    }

    public void completeWorkflow() {

    }

//    public void updateTask(TaskResult taskResult) {
//        if (taskResult == null) {
//            throw new ExecutionException(ExecutionException.Code.INVALID_INPUT, "TaskResult object is null");
//        }
//
//        String workflowId = taskResult.getWorkflowInstanceId();
//        Workflow workflowInstance = executionDAOFacade.getWorkflowById(workflowId, true);
//
//        if (workflowInstance.getWorkflowDefinition() == null) {
//            workflowInstance = metadataMapperService.populateWorkflowWithDefinitions(workflowInstance);
//        }
//
//        Task task = executionDAOFacade.getTaskById(taskResult.getTaskId());
//
//        logger.debug("Task: {} being updated", task.getTaskId());
//
//        String taskQueueName = QueueUtils.getQueueName(task);
//
//        if (task.getStatus().isTerminal()) {
//            // Task was already updated....
//            queueDAO.remove(taskQueueName, taskResult.getTaskId());
//            logger.info("Task: {} has already finished execution with status: {} within workflow: {}. Removed task from queue: {}", task.getTaskId(), task.getStatus(), task.getWorkflowInstanceId(), taskQueueName);
//            return;
//        }
//
//        if (workflowInstance.getStatus().isTerminal()) {
//            // Workflow is in terminal state
//            queueDAO.remove(taskQueueName, taskResult.getTaskId());
//            logger.info("Workflow: {} has already finished execution. Task update for: {} ignored and removed from Queue: {}.", workflowInstance, taskResult.getTaskId(), taskQueueName);
//            return;
//        }
//
//        task.setStatus(Task.Status.SCHEDULED);
//        task.setWorkerId(taskResult.getWorkerId());
//        task.setOutputData(taskResult.getOutputData());
//
//        deciderService.externalizeTaskData(task);
//
//        if (task.getStatus().isTerminal()) {
//            task.setEndTime(System.currentTimeMillis());
//        }
//
//        decide(workflowId);
//
//        if (task.getStatus().isTerminal()) {
//            long duration = getTaskDuration(0, task);
//            long lastDuration = task.getEndTime() - task.getStartTime();
//            // Monitors.recordTaskExecutionTime(task.getTaskDefName(), duration, true, task.getStatus());
//            // Monitors.recordTaskExecutionTime(task.getTaskDefName(), lastDuration, false, task.getStatus());
//        }
//    }

//    public Task getTask(String taskId) {
//        return getTaskById(taskId)
//                .map(task -> {
//                    if (task.getWorkflowTask() != null) {
//                        return metadataMapperService.populateTaskWithDefinition(task);
//                    }
//                    return task;
//                })
//                .orElse(null);
//    }

}
