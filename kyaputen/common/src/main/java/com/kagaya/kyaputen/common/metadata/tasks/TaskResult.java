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

    public TaskResult(Task task) {

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
}


