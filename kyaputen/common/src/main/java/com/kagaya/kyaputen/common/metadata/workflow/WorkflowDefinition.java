package com.kagaya.kyaputen.common.metadata.workflow;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowDefinition {

    private String name;

    private String description;

    private int version = 1;

    private TaskDefinition startTaskDefinition;

    private Map<String, TaskDefinition> taskDefs = new HashMap<>();

    private List<String> inputParameters;

    private List<String> outputParameters;

    private long timeoutSeconds;

    private long timeLimit;

    // 资源分配列表 CE
    private Map<String, Double> resourceMap = new HashMap<>();


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

    public Map<String, Integer> getTaskTypeNums() {
        Map<String, Integer> taskTypeNum = new HashMap<>();

        for (TaskDefinition td: taskDefs.values()) {
            String taskType = td.getTaskType();
            Integer num = taskTypeNum.get(taskType);
            if (num == null) {
                taskTypeNum.put(taskType, 1);
            } else {
                num++;
                taskTypeNum.put(taskType, num);
            }
        }

        return taskTypeNum;
    }

    public TaskDefinition getTaskDef(String taskName) {
        return taskDefs.get(taskName);
    }

    public TaskDefinition getStartTaskDefinition() {
        return startTaskDefinition;
    }

    public void setStartTaskDefinition(TaskDefinition startTaskDefinition) {
        this.startTaskDefinition = startTaskDefinition;
    }

    public Map<String, TaskDefinition> getTaskDefs() {
        return taskDefs;
    }

    public void setTaskDefs(Map<String, TaskDefinition> tasks) {
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

    public Double getPodResource(String taskType) {
        return resourceMap.get(taskType);
    }

    public void setPodResource(String taskType, double cu) {
        resourceMap.put(taskType, cu);
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
