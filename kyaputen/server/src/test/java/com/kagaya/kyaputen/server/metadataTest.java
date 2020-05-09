package com.kagaya.kyaputen.server;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.server.metadata.MetadataService;

public class metadataTest {

    public static void main(String[] args) {
        MetadataService service = new MetadataService();

        WorkflowDefinition wd = service.readWorkflowConfig("/Users/kagaya/Documents/repos/microservice/config/workflow-test.json");

        System.out.println(wd);
    }
}
