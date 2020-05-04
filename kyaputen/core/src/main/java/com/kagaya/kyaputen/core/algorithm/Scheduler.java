package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;


public interface Scheduler {

    void calcWorkflowCostEfficient(WorkflowDefinition workflowDef, double deadlineFactor);

    ExecutionPlan genExecutionPlan(long startTime, WorkflowDefinition workflowDef);

    void deploy(ExecutionPlan executionPlan);
}