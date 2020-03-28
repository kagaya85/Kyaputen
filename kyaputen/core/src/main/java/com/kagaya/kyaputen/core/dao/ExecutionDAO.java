package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.List;

public interface ExecutionDAO {

    List<Task> getTasks(String taskType, String startKey, int count);

    void updateTask(Task task);

    boolean removeTask(String taskId);

    Task getTask(String taskId);

    String createWorkflow(Workflow workflow);

    String updateWorkflow(Workflow workflow);

    Workflow getWorkflow(String workflowId);

    long getInProgressTaskCount();
}
