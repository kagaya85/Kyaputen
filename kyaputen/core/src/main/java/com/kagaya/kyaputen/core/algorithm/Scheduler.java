package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

public interface Scheduler {

    void calcWorkflowCostEfficient(WorkflowDefinition workflowDef, double deadlineFactor);


}