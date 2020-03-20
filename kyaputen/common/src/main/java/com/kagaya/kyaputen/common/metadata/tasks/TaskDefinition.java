package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskDefinition {

    private String name;

    private String description;

    private int retryCount = 3;

    private long timeoutSeconds;

    private List<String> inputKeys = new ArrayList<String>();

    private List<String> outputKeys = new ArrayList<String>();

    private int retryDelaySeconds = 60;

    private long responseTimeoutSeconds = 60 * 60;

    public TaskDefinition() {

    }

    public TaskDefinition(String name) {
        this.name = name;
    }

    public TaskDefinition(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TaskDefinition(String name, String description, int retryCount, long timeoutSeconds) {
        this.name = name;
        this.description = description;
        this.retryCount = retryCount;
        this.timeoutSeconds = timeoutSeconds;
    }

    public TaskDefinition(String name, String description, int retryCount, long timeoutSeconds, long responseTimeoutSeconds) {
        this.name = name;
        this.description = description;
        this.retryCount = retryCount;
        this.timeoutSeconds = timeoutSeconds;
        this.responseTimeoutSeconds = responseTimeoutSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Task name: " + name;
    }
}

