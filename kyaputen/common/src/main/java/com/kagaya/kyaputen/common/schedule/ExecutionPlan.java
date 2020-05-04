package com.kagaya.kyaputen.common.schedule;

        import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
        import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.Map;

public class ExecutionPlan {

    private Map<String, TaskExecutionPlan> executionPlanMap = new HashMap<>();

    public ExecutionPlan(WorkflowDefinition workflowDef) {
        for (String name: workflowDef.getTaskDefNames()) {
            TaskDefinition taskDef = workflowDef.getTaskDef(name);
            executionPlanMap.put(name, new TaskExecutionPlan(name, taskDef.getTaskType()));
        }
    }

    public void setTaskExecutionPlan(String taskName, TaskExecutionPlan plan) {
        executionPlanMap.put(taskName, plan);
    }

    public TaskExecutionPlan getTaskExecutionPlan(String taskName) {
        return executionPlanMap.get(taskName);
    }

}
