package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.util.List;
import java.util.Map;

public class WorkflowDefinitionDAO {

    private static Map<String, WorkflowDefinition> workflowMetaData;

    public WorkflowDefinition get(String workflowName) {

        return workflowMetaData.get(workflowName);
    }

    public void add(WorkflowDefinition workflowDef) {

        workflowMetaData.put(workflowDef.getName(), workflowDef);
    }

    public void addAll(List<WorkflowDefinition> workflowDefs) {
        for(WorkflowDefinition wd: workflowDefs) {
            workflowMetaData.put(wd.getName(), wd);
        }
    }
}
