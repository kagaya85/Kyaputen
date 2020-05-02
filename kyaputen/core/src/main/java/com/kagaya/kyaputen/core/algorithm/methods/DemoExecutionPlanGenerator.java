package com.kagaya.kyaputen.core.algorithm.methods;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;

public class DemoExecutionPlanGenerator implements Method {

    private ExecutionPlan exePlan;

    @Override
    public ExecutionPlan schedule(long startTime, WorkflowDefinition workflowDef) {

        exePlan = new ExecutionPlan();


        genExecutionPlan(startTime, workflowDef);

        return exePlan;
    }

    private void genExecutionPlan(long startTime, WorkflowDefinition workflowDef) {
        exePlan.setTaskExecutionPlan();
    }

    private TaskExecutionPlan genTaskExecutionPlan(String taskName, WorkflowDefinition workflowDef) {

    }

    private void calcUrgencyLevel() {

    }
}
