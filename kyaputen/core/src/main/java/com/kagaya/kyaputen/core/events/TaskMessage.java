package com.kagaya.kyaputen.core.events;


public class TaskMessage {

    private final String workflowInstanceId;

    private final String taskId;

    private String workerId;

    private int priority;

    public TaskMessage(String workflowInstanceId, String taskId, int priority, String workerId) {
        this.workflowInstanceId = workflowInstanceId;
        this.taskId = taskId;
        this.priority = priority;
        this.workerId = workerId;
    }

    /**
     * @return the workflowInstanceId
     */
    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    /**
     * @return the id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Gets the message priority
     * @return priority of message.
     */
    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return taskId;
    }


}