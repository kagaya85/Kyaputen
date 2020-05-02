package com.kagaya.kyaputen.common.schedule;

        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.Map;

public class ExecutionPlan {

    private Map<String, TaskExecutionPlan> executionPlanMap = new HashMap<>();

    private List<String> urgentLevelQueue = new LinkedList<>();

    public void setTaskExecutionPlan(String taskName, TaskExecutionPlan plan) {
        executionPlanMap.put(taskName, plan);
    }

    public TaskExecutionPlan getTaskExecutionPlan(String taskName) {
        return executionPlanMap.get(taskName);
    }
}
