package com.kagaya.kyaputen.common.metadata.workflow;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流定义
 * - 单输入节点单输出节点
 */
public class WorkflowDefinition {

    private String name;

    private String description;

    private int version = 1;

    private Map<String, TaskDefinition> taskDefs = new HashMap<>();

    private List<String> inputParameters;

    private List<String> outputParameters;

    private long timeoutSeconds;

    private long timeLimit;

    // 资源分配列表 CE
    private Map<String, Double> CostEfficientMap = new HashMap<>();

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

    /**
     *
     * @param taskType
     * @return taskSize if exists, or 0;
     */
    public long getTaskSize(String taskType) {
        for (TaskDefinition taskDef: taskDefs.values()) {
            if (taskDef.getTaskType().equals(taskType))
                return taskDef.getTaskSize();
        }

        return 0L;
    }

    public TaskDefinition getStartTaskDefinition() {
        
        for (TaskDefinition td: taskDefs.values()) {
            if (td.getPriorTasks().isEmpty())
            return td;
        }

        return null;
    }

    public TaskDefinition getEndTaskDefinition() {
        for (TaskDefinition td: taskDefs.values()) {
            if (td.getNextTasks().isEmpty())
            return td;
        }

        return null;
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

    public Double getCeByType(String taskType) {
        return CostEfficientMap.get(taskType);
    }

    public void setCEMap(Map<String, Double> CostEfficientMap) {
        this.CostEfficientMap = CostEfficientMap;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
