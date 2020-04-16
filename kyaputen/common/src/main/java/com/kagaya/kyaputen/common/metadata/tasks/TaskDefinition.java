package com.kagaya.kyaputen.common.metadata.tasks;

import com.kagaya.kyaputen.common.runtime.Pod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskDefinition {

    private String taskDefName;

    private String description;

    private int retryCount = 3;

    private long timeoutSeconds;

    private List<String> inputKeys = new ArrayList<>();

    private List<String> outputKeys = new ArrayList<>();

    private List<String> priorTasks = new ArrayList<>();

    private List<String> nextTasks = new ArrayList<>();

    private int retryDelaySeconds = 60;

    private long responseTimeoutSeconds = 60 * 60;

    private int req;

    private int priority = -1;

    // 资源分配量
    private Pod resource;

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

    public Pod getResource() {
        return resource;
    }

    public void setResource(Pod resource) {
        this.resource = resource;
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

