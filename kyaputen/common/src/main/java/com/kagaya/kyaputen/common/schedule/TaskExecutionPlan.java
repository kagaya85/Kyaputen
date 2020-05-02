package com.kagaya.kyaputen.common.schedule;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

public class TaskExecutionPlan {

    private TaskDefinition taskDef;

    private String podId;

    private String nodeId;

    private long startTime;

    private long finishTime;

    private double urgencyLevel;

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

    public TaskDefinition getTaskDef() {
        return taskDef;
    }

    public void setTaskDef(TaskDefinition taskDef) {
        this.taskDef = taskDef;
    }

    public double getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(double urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }
}
