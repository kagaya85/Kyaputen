package com.kagaya.kyaputen.server;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.server.metadata.MetadataService;

import java.util.List;

public class MetadataTest {

    public static void main(String[] args) {
        MetadataService service = new MetadataService();

        List<WorkflowDefinition> wd = service.readWorkflowConfig("/Users/kagaya/Documents/repos/microservice/config/workflow-test.json");

    }
}
