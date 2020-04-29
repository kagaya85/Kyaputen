package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.*;

public class TaskDefinition {

    private String taskDefName;

    private String taskType;

    private String description;

    private int retryCount = 3;

    private long timeoutSeconds;

    private List<String> inputKeys = new ArrayList<>();

    private List<String> outputKeys = new ArrayList<>();

    private List<String> priorTasks = new ArrayList<>();

    private List<String> nextTasks = new ArrayList<>();

    private Map<String, Long> dataTransSizeMap = new HashMap<>();

    private int retryDelaySeconds = 60;

    private long responseTimeoutSeconds = 60 * 60;

    private int req;

    private int priority = -1;

    private long timeLimit;

    // 任务尺寸，即一个cu单位下执行所需的时间，单位ms
    private double taskSize = 1000;

    // 期待启动时间，以0时刻为起点的绝对时间
    private long expectedStartTime;

    // 期望完成时间，与ce有关
    private long expectedFinishTime;

    public TaskDefinition() {

    }

    public TaskDefinition(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    public TaskDefinition(String taskDefName, String description) {
        this.taskDefName = taskDefName;
        this.description = description;
    }

    public TaskDefinition(String taskDefName, String description, int retryCount, long timeoutSeconds) {
        this.taskDefName = taskDefName;
        this.description = description;
        this.retryCount = retryCount;
        this.timeoutSeconds = timeoutSeconds;
    }

    public TaskDefinition(String taskDefName, String description, int retryCount, long timeoutSeconds, long responseTimeoutSeconds) {
        this.taskDefName = taskDefName;
        this.description = description;
        this.retryCount = retryCount;
        this.timeoutSeconds = timeoutSeconds;
        this.responseTimeoutSeconds = responseTimeoutSeconds;
    }

    public String getTaskDefName() {
        return taskDefName;
    }

    public void setTaskDefName(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryDelaySeconds() {
        return retryDelaySeconds;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getInputKeys() {
        return inputKeys;
    }

    public void setInputKeys(List<String> inputKeys) {
        this.inputKeys = inputKeys;
    }

    public List<String> getOutputKeys() {
        return outputKeys;
    }

    public void setOutputKeys(List<String> outputKeys) {
        this.outputKeys = outputKeys;
    }

    public long getResponseTimeoutSeconds() {
        return responseTimeoutSeconds;
    }

    public void setResponseTimeoutSeconds(long responseTimeoutSeconds) {
        this.responseTimeoutSeconds = responseTimeoutSeconds;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setRetryDelaySeconds(int retryDelaySeconds) {
        this.retryDelaySeconds = retryDelaySeconds;
    }

    public String getDescription() {
        return description;
    }

    public void setTimeoutSeconds(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getPriorTasks() {
        return priorTasks;
    }

    public void setPriorTasks(List<String> priorTasks) {
        this.priorTasks = priorTasks;
    }

    public List<String> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(List<String> nextTasks) {
        this.nextTasks = nextTasks;
    }

    public int getReq() {
        return req;
    }

    public void setReq(int req) {
        this.req = req;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public double getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(double taskSize) {
        this.taskSize = taskSize;
    }

    public void updateTaskSize(double cu, long executionTimeMs) {
        this.taskSize = executionTimeMs / cu;
    }

    public long getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(long expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public long getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(long expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    /**
     * 获取与目标任务间的数据传输量
     * @param taskDefName 目标任务名
     * @return
     */
    public long getDataSize(String taskDefName) {

        Long size = dataTransSizeMap.get(taskDefName);

        if (size == null) {
            return 0;
        } else {
            return size;
        }
    }

    public void setDataTransSize(String taskDefName, long dataSize) {
        dataTransSizeMap.put(taskDefName, dataSize);
    }

    public boolean isStartTask() {
        return priorTasks.isEmpty();
    }

    public boolean isEndTask() {
        return nextTasks.isEmpty();
    }

    @Override
    public String toString() {
        return "Task name: " + taskDefName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDefinition that = (TaskDefinition) o;
        return getTaskDefName().equals(that.getTaskDefName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskDefName());
    }
}

