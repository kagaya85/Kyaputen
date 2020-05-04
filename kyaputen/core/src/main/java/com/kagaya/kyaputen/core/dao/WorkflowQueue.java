package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkflowQueue {

    // 改成Map？？？
    private static Map<String, Workflow> workflowMap = new HashMap<>();

    public Workflow getById(String workflowId) {
        return workflowMap.get(workflowId);
    }

    public List<Workflow> getByName(String workflowName) {
        List<Workflow> workflowList = new LinkedList<>();

        for(Workflow workflow: workflowMap.values()) {
            if (workflow.getName().equals(workflowName))
                workflowList.add(workflow);
        }

        return workflowList;
    }

    public void add(Workflow workflow) {
        workflowMap.put(workflow.getWorkflowId(), workflow);
    }

    public void remove(String workflowId) {
        workflowMap.remove(workflowId);
    }

}
