package com.kagaya.kyaputen.common.runtime;

/**
 * @description: 任务资源量定义
 */
public class PodResource {

    private int cpuNeed;

    private int memNeed;

    private int cpuAllocated;

    private int memAllocated;

    private String computeUnitId;

    public PodResource() {}

    public int getCpuNeeded() {
        return cpuNeed;
    }

    public void setCpuNeeded(int cpuNeed) {
        this.cpuNeed = cpuNeed;
    }

    public int getMemNeeded() {
        return memNeed;
    }

    public void setMemNeeded(int memNeeded) {
        this.memNeed = memNeed;
    }

    public int getCpuAllocated() {
        return cpuAllocated;
    }

    public void setCpuAllocated(int cpuAllocated) {
        this.cpuAllocated = cpuAllocated;
    }

    public int getMemAllocated() {
        return memAllocated;
    }

    public void setMemAllocated(int memAllocated) {
        this.memAllocated = memAllocated;
    }

    public void setComputeUnitId(String computeUnitId) {
        this.computeUnitId = computeUnitId;
    }

    public String getComputeUnitId() {
        return computeUnitId;
    }
}
