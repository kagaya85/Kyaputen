package com.kagaya.kyaputen.common.runtime;

public class VmResource {

    private int startTime;

    private int price;

    private int cpu;

    private int mem;

    public VmResource() {}

    public VmResource(int cpu, int mem) {
        this.cpu = cpu;
        this.mem = mem;
    }

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
}