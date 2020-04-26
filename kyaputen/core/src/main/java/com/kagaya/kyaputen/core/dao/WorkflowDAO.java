package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.util.Map;

public class WorkflowDAO {

    private static Map<String, WorkflowDefinition> workflowMetaData;

    public WorkflowDefinition get(String workflowName) {

        return workflowMetaData.get(workflowName);
    }

    public void set(WorkflowDefinition workflowDef) {

        workflowMetaData.put(workflowDef.getName(), workflowDef);
    }
}
