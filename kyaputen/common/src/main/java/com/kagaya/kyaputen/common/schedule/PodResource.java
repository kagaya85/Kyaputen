package com.kagaya.kyaputen.common.schedule;

public class PodResource {

    private String taskImageName;

    private int computeUnit;

    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getComputeUnit() {
        return computeUnit;
    }

    public void setComputeUnit(int computeUnit) {
        this.computeUnit = computeUnit;
    }

    public String getTaskImageName() {
        return taskImageName;
    }

    public void setTaskImageName(String taskImageName) {
        this.taskImageName = taskImageName;
    }
}
