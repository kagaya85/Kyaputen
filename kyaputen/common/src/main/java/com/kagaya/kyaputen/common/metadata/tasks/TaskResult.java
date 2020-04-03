package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.Map;
import java.util.HashMap;


public class TaskResult {
    public enum Status {
        IN_PROGRESS, FAILED, FAILED_WITH_TERMINAL_ERROR, COMPLETED
    }

    private String workflowInstanceId;

    private String taskId;

    private String workerId;

    private Status status;

    private Map<String, Object> outputData = new HashMap<>();

    public TaskResult() {}

    public TaskResult(Task task) {
        this.workflowInstanceId = task.getWorkflowInstanceId();
        this.taskId = this.getTaskId();
        this.workerId = this.getWorkerId();
        this.outputData = this.getOutputData();

        switch (task.getStatus()) {
            case CANCELED:
            case COMPLETED_WITH_ERRORS:
            case TIMED_OUT:
            case SKIPPED:
                this.status = Status.FAILED;
                break;
            case SCHEDULED:
                this.status = Status.IN_PROGRESS;
                break;
            default:
                this.status = Status.valueOf(task.getStatus().name());
                break;
        }
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setOutputData(Map<String, Object> outputData) {
        this.outputData = outputData;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public void setWorkflowInstanceId(String workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public TaskResult copy() {
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowInstanceId);
        taskResult.setTaskId(taskId);
        taskResult.setWorkerId(workerId);
        taskResult.setStatus(status);
        taskResult.setOutputData(outputData);
        return taskResult;
    }
}


