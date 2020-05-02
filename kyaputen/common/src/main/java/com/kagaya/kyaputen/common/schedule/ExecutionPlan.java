package com.kagaya.kyaputen.common.schedule;

        import java.util.HashMap;
        import java.util.Map;

public class ExecutionPlan {

    private Map<String, TaskExecutionPlan> executionPlanMap = new HashMap<>();

    public void setTaskExecutionPlan(String taskName, TaskExecutionPlan plan) {
        executionPlanMap.put(taskName, plan);
    }

    public TaskExecutionPlan getTaskExecutionPlan(String taskName) {
        return executionPlanMap.get(taskName);
    }
}
