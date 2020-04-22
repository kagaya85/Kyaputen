package com.kagaya.kyaputen.common.runtime;

/**
 * @description: 任务资源量定义
 */
public class Pod {

    private String taskImageName;

    private int cpu;

    private int mem;

    private PodStatus status;

    private String nodeId;

    private long startTime;

    public Pod() {}

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getMem() {
        return mem;
    }

    public void setMem(int mem) {
        this.mem = mem;
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
        IDLE, BUSY, DOWN, ERROR
    }
}
