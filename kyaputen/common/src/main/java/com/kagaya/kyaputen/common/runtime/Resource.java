package com.kagaya.kyaputen.common.runtime;

/**
 * @description: 任务资源量定义
 */
public class Resource {

    private int cpuNeeded;

    private int memNeeded;

    private int cpuAllocated;

    private int memAllocated;

    public Resource() {}

    public int getCpuNeeded() {
        return cpuNeeded;
    }

    public void setCpuNeeded(int cpuNeeded) {
        this.cpuNeeded = cpuNeeded;
    }

    public int getMemNeeded() {
        return memNeeded;
    }

    public void setMemNeeded(int memNeeded) {
        this.memNeeded = memNeeded;
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
}
