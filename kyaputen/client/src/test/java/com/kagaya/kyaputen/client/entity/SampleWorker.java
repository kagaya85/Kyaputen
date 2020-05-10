package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.worker.Worker;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult.Status;

public class SampleWorker implements Worker {

    private String taskDefName;

    public SampleWorker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);

        System.out.println("Input:" + task.getInputData());

        result.getOutputData().put("level", "test");
        result.getOutputData().put("score", 1551);

        result.setStatus(Status.COMPLETED);

        return result;
    }
}
