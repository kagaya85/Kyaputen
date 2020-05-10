package com.kagaya.kyaputen.common.schedule;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

public class TaskExecutionPlan {

    private String taskName;

    private String taskId;

    private String taskType;

    private String podId;

    private String nodeId;

    private String expectedStartTime;

    // 预期执行时间 ms
    private long executionTime;

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

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setExpectedStartTime(String expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public String getExpectedStartTime() {
        return expectedStartTime;
    }

    @Override
    public String toString() {
        return "TaskExecutionPlan{" +
                "taskName='" + taskName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", podId='" + podId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", expectedStartTime='" + expectedStartTime + '\'' +
                ", executionTime=" + executionTime +
                ", urgencyLevel=" + urgencyLevel +
                '}';
    }
}
