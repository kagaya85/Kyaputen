package com.kagaya.kyaputen.common.metadata.workflow;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkflowDefinition {

    private String name;

    private String description;

    private int version = 1;

    private List<TaskDefinition> taskDefs = new LinkedList<>();

    private List<String> inputParameters = new LinkedList<>();

    private List<String> outputParameters = new LinkedList<>();

    private long timeoutSeconds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<String> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<String> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public List<TaskDefinition> getTaskDefs() {
        return taskDefs;
    }

    public void setTaskDefs(List<TaskDefinition> tasks) {
        this.taskDefs = tasks;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public List<String> getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(List<String> outputParameters) {
        this.outputParameters = outputParameters;
    }
}
