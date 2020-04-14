package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.LinkedList;
import java.util.List;

public class WorkflowQueue {

    private static List<Workflow> workflowQueue = new LinkedList<>();

    public Workflow getById(String workflowId) {

        for (Workflow workflow: workflowQueue) {
            if (workflow.getWorkflowId().equals(workflowId))
                return workflow;
        }

        return null;
    }

    public List<Workflow> getByName(String workflowName) {
        List<Workflow> workflowList = new LinkedList<>();

        for(Workflow workflow: workflowQueue) {
            if (workflow.getName().equals(workflowName))
                workflowList.add(workflow);
        }

        return workflowList;
    }

    public void add(Workflow workflow) {
        workflowQueue.add(workflow);
    }

    public boolean remove(String workflowId) {
        return workflowQueue.removeIf(workflow -> workflow.getWorkflowId().equals(workflowId));
    }

}
