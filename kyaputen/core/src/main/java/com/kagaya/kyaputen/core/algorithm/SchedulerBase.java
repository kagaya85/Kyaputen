package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

public interface SchedulerBase {

    void calcResource(WorkflowDefinition workflowDef, double deadlineFactor);


}