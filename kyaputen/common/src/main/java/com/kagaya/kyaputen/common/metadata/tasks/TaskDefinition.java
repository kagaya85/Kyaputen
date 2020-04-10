package com.kagaya.kyaputen.common.metadata.tasks;

import com.kagaya.kyaputen.common.runtime.PodResource;

import java.util.ArrayList;
import java.util.List;

public class TaskDefinition {

    private String taskDefName;

    private String description;

    private int retryCount = 3;

    private long timeoutSeconds;

    private List<String> inputKeys = new ArrayList<String>();

    private List<String> outputKeys = new ArrayList<String>();

    private int retryDelaySeconds = 60;

    private long responseTimeoutSeconds = 60 * 60;

    // 资源分配量
    private PodResource resource;

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

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Task name: " + taskDefName;
    }
}

