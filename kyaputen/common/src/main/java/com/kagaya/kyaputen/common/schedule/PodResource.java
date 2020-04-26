package com.kagaya.kyaputen.common.schedule;

/**
 * 静态资源量对象，用于定义WorkflowDefinition中的资源需求
 */
public class PodResource {

    private double computeUnit;

    private double cpu;

    private double mem;

    public PodResource() {

    }

    public PodResource(double cpu, double mem) {
        this.cpu = cpu;
        this.mem = mem;
        // 计算单元暂用cpu代替
        this.computeUnit = cpu;
    }

    public double getComputeUnit() {
        return computeUnit;
    }

    public void setComputeUnit(double computeUnit) {
        this.computeUnit = computeUnit;
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
}
