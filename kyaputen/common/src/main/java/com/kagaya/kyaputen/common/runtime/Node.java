package com.kagaya.kyaputen.common.runtime;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private String id;

    private NodeStatus status;

    private int price;

    private int cpu;

    private int mem;

    private long startTime;

    private List<Pod> podList = new LinkedList<>();

    private List<String> imageList = new LinkedList<>();

    public Node() {}

    public Node(int cpu, int mem, NodeStatus status) {
        this.cpu = cpu;
        this.mem = mem;
        this.status = status;
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

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public Boolean isImageLoaded(String imageName) {

        return false;
    }

    public void addImage(String imageName) {

    }

    public Boolean isDeployed() {
        return status.isDeployed();
    }

    public Boolean isRunning() {
        return status.isRunning();
    }

    public Boolean isDown() {
        return status.isDown();
    }

    public enum NodeStatus {
        
        NewNode(false, false, false), Running(true, true, false), Idle(true, false, false), Down(true, false, true);

        private Boolean deployed;

        private Boolean running;

        private Boolean down;

        NodeStatus(Boolean deployed, Boolean running, Boolean down) {
            this.deployed = deployed;
            this.running = running;
            this.down = down;
        }

        Boolean isDeployed() {
            return deployed;
        }

        Boolean isRunning() {
            return running;
        }

        Boolean isDown() {
            return down;
        }

    }
}

