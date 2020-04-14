package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.LinkedList;
import java.util.List;

public class WorkflowRunQueue {

    private static List<Workflow> workflowQueue = new LinkedList<>();

    public Workflow get(String workflowId) {

        for (Workflow workflow: workflowQueue) {
            if (workflow.getWorkflowId().equals(workflowId))
                return workflow;
        }

        return null;
    }

    public void add(Workflow workflow) {

        workflowQueue.add(workflow);
    }

    public boolean remove(String workflowId) {

        return false;
    }

}
