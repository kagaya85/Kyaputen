package com.kagaya.kyaputen.common.runtime;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private String id;

    private String nodeType;

    private NodeStatus status;

    private double price;

    private double cpu;

    private double mem;

    private double totalComputeUnit;

    private double allocatedComputeUnit;

    private long startTime;

    private List<String> podList = new LinkedList<>();

    private List<String> imageList = new LinkedList<>();

    public Node() {}

    public Node(int cpu, int mem, NodeStatus status) {
        this.cpu = cpu;
        this.mem = mem;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getPodList() {
        return podList;
    }

    public void setPodList(List<String> podList) {
        this.podList = podList;
    }

    public void addPod(String podId) { this.podList.add(podId); }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public Boolean isImageLoaded(String imageName) {
        return imageList.contains(imageName);
    }

    public void addImage(String imageName) {
        imageList.add(imageName);
    }

    public void removeImage(String imageName) {
        imageList.remove(imageName);
    }

    public double getTotalComputeUnit() {
        return totalComputeUnit;
    }

    public void setTotalComputeUnit(double totalComputeUnit) {
        this.totalComputeUnit = totalComputeUnit;
    }

    public double getRemainComputeUnit() {
        return totalComputeUnit - allocatedComputeUnit;
    }

    public double getAllocatedComputeUnit() {
        return allocatedComputeUnit;
    }

    public void setAllocatedComputeUnit(double allocatedComputeUnit) {
        this.allocatedComputeUnit = allocatedComputeUnit;
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

