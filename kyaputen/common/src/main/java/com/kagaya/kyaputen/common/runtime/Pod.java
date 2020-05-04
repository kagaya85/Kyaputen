package com.kagaya.kyaputen.common.runtime;

import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    // 当前CU下的价格
    private double price;

    private static final String PullImageTaskId = "*PullImageTask*";

    // 任务执行计划队列
    public List<TaskExecutionPlan> executionPlanQueue = new LinkedList<>();

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

    /**
     * 获取最快新任务开始时间
     * @return time ms
     */
    public long getEarliestStartTime() {

        long totTime = 0;

        for (TaskExecutionPlan p: executionPlanQueue) {
            totTime += p.getExecutionTime();
        }

        return System.currentTimeMillis() + totTime;
    }

    public void addTaskExecutionPlan(TaskExecutionPlan plan) {
        // 加入新计划
        executionPlanQueue.add(plan);
    }

    /**
     * 完成任务计划
     */
    public void finishTaskExecutionPlan(String taskId) {

        for (int i = 0; i < executionPlanQueue.size(); i++) {
            TaskExecutionPlan plan = executionPlanQueue.get(i);
            if (plan.getTaskId().equals(taskId)) {
                executionPlanQueue.remove(i);
            }
        }
    }

    public void addPullImageTask(long pullTime) {
        TaskExecutionPlan plan = new TaskExecutionPlan("PullImage", "PullImage");
        plan.setTaskId(PullImageTaskId);
        plan.setExecutionTime(pullTime);
    }

    public void finishPullImage() {
        for (int i = 0; i < executionPlanQueue.size(); i++) {
            TaskExecutionPlan plan = executionPlanQueue.get(i);
            if (plan.getTaskId().equals(PullImageTaskId)) {
                executionPlanQueue.remove(i);
            }
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public enum PodStatus {
        NEW, IDLE, RUNNING, DOWN, ERROR
    }
}
