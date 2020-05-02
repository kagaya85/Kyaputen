package com.kagaya.kyaputen.common.schedule;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

public class TaskExecutionPlan {

    private String taskName;

    private String taskType;

    private String podId;

    private String nodeId;

    private long startTime;

    private long finishTime;

    private double urgencyLevel;

    public TaskExecutionPlan(String taskName, String taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPodId() {
        return podId;
    }

    public void setPodId(String podId) {
        this.podId = podId;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public double getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(double urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }
}
