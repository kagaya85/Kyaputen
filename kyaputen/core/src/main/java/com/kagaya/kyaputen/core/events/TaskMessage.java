package com.kagaya.kyaputen.core.events;


public class TaskMessage {

    private final String workflowInstanceId;

    private final String taskId;

    private int priority;

    public TaskMessage(String workflowInstanceId, String taskId, int priority) {
        this.workflowInstanceId = workflowInstanceId;
        this.taskId = taskId;
        this.priority = priority;
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

    @Override
    public String toString() {
        return taskId;
    }


}