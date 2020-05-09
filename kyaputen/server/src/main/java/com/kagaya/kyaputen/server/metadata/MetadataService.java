package com.kagaya.kyaputen.server.metadata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MetadataService {

    public WorkflowDefinition readWorkflowConfig(String filepath) {
        WorkflowBean workflowData = new WorkflowBean();

        Gson gson = new GsonBuilder().create();

        try {
            JsonReader reader = new JsonReader(new FileReader(filepath));
            workflowData = gson.fromJson(reader, WorkflowBean.class);
        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }

        System.out.println(workflowData.name);

        WorkflowDefinition workflowDef = new WorkflowDefinition();

        Map<String, TaskDefinition> tasksMap = new HashMap<>();

        for (WorkflowBean.TaskInfo t: workflowData.tasks) {
            TaskDefinition td = new TaskDefinition();
            td.setTaskDefName(t.taskName);
            td.setTaskType(t.taskType);
            td.setDescription(t.description);
            td.setTimeoutSeconds(t.timeoutSeconds);
            td.setInputKeys(t.inputKeys);
            td.setOutputKeys(t.outputKeys);
            td.setPriorTasks(t.priorTasks);
            td.setNextTasks(t.nextTasks);
            tasksMap.put(t.taskName, td);
        }

        workflowDef.setName(workflowData.name);
        workflowDef.setDescription(workflowData.description);
        workflowDef.setTimeLimit(workflowData.timeLimit);
        workflowDef.setVersion(workflowData.version);
        workflowDef.setTaskDefs(tasksMap);

        return workflowDef;
    }
}
