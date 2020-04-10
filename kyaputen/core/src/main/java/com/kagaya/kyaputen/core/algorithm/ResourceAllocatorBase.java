package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

public interface ResourceAllocatorBase {

    void allocateResource(WorkflowDefinition workflowDef);


}