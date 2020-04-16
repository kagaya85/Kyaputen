package com.kagaya.kyaputen.common.runtime;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private int price;

    private int cpu;

    private int mem;

    private long startTime;

    private List<Pod> podList = new LinkedList<>();

    public Node() {}

    public Node(int cpu, int mem) {
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Pod> getPodList() {
        return podList;
    }

    public void setPodList(List<Pod> podList) {
        this.podList = podList;
    }
}