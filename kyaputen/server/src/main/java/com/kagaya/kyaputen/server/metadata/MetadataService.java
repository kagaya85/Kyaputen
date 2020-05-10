package com.kagaya.kyaputen.server.metadata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetadataService {

    public List<WorkflowDefinition> readWorkflowConfig(String filepath) {

        Gson gson = new GsonBuilder().create();
        List<WorkflowDefinition> workflowDefs = new LinkedList<>();

        try {
            JsonReader reader = new JsonReader(new FileReader(filepath));
            WorkflowBean[] workflowDatas = gson.fromJson(reader, WorkflowBean[].class);

            for (WorkflowBean data: workflowDatas) {

                WorkflowDefinition workflowDef = new WorkflowDefinition();
                Map<String, TaskDefinition> tasksMap = new HashMap<>();

                for (WorkflowBean.TaskInfo t: data.tasks) {
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

                workflowDef.setInputParameters(data.tasks.get(0).inputKeys);
                workflowDef.setOutputParameters(data.tasks.get(data.tasks.size() - 1).outputKeys);
                workflowDef.setName(data.name);
                workflowDef.setDescription(data.description);
                workflowDef.setTimeLimit(data.timeLimit);
                workflowDef.setVersion(data.version);
                workflowDef.setTaskDefs(tasksMap);

                workflowDefs.add(workflowDef);
            }
        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            System.exit(-1);
        }

        return workflowDefs;
    }
}
