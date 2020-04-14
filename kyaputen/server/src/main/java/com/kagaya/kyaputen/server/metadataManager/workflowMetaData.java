package com.kagaya.kyaputen.server.metadataManager;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.util.List;

public class workflowMetaData {

    private static List<WorkflowDefinition> workflowMetaDataList;

    public WorkflowDefinition get(String WorkflowName) {

        for (WorkflowDefinition wfd: workflowMetaDataList) {
            if (wfd.getName().equals(WorkflowName))
                return wfd;
        }
        return null;
    }

    public void set(WorkflowDefinition workflowDef) {

        workflowMetaDataList.add(workflowDef);
    }
}
