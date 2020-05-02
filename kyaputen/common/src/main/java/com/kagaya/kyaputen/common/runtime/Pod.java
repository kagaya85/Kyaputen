package com.kagaya.kyaputen.common.runtime;

/**
 * @description: 任务资源量定义
 */
public class Pod {

    private String podId;

    private String taskImageName;

    private double cpu;

    private double mem;

    private double computeUnit;

    private PodStatus status;

    private String nodeId;

    private long startTime;

    public Pod() {}

    public String getPodId() {
        return podId;
    }

    public void setPodId(String podId) {
        this.podId = podId;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public double getMem() {
        return mem;
    }

    public void setMem(double mem) {
        this.mem = mem;
    }

    public double getComputeUnit() {
        return computeUnit;
    }

    public void setComputeUnit(double computeUnit) {
        this.computeUnit = computeUnit;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public PodStatus getStatus() {
        return status;
    }

    public void setStatus(PodStatus status) {
        this.status = status;
    }

    public String getTaskImageName() {
        return taskImageName;
    }

    public void setTaskImageName(String taskImageName) {
        this.taskImageName = taskImageName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public enum PodStatus {
        NEW, IDLE, RUNNING, DOWN, ERROR
    }
}
