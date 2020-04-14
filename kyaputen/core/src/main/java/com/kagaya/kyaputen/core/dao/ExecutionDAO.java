package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.List;

public interface ExecutionDAO {

    void updateTask(Task task, TaskResult taskResult);

    boolean removeTask(String taskId);

    Task getTask(String workflowId, String taskId);

    boolean createWorkflow(WorkflowDefinition workflowDef);

    void updateWorkflow(Workflow workflow);

    Workflow getWorkflow(String workflowId);

    long getInProgressTaskCount();

    PollData getPollData(String taskDefName, String domain);
}
