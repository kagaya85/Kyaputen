package com.kagaya.kyaputen.core.metadata;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.util.Map;

public class WorkflowMetadata {

    private static Map<String, WorkflowDefinition> workflowMetaData;

    public WorkflowDefinition get(String workflowName) {

        return workflowMetaData.get(workflowName);
    }

    public void set(WorkflowDefinition workflowDef) {

        workflowMetaData.put(workflowDef.getName(), workflowDef);
    }
}
