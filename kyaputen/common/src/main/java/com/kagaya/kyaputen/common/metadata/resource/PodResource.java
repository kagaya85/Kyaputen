package com.kagaya.kyaputen.common.metadata.resource;

/**
 * - 静态资源量对象，用于定义WorkflowDefinition中的资源需求
 */
public class PodResource {

    private double cpu;

    private double mem;

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
}
