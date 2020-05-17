package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.worker.Worker;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult.Status;

public class SampleWorker implements Worker {

    private String taskType;

    public SampleWorker(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getTaskDefName() {
        return taskType;
    }

    @Override
    public TaskResult execute(Task task) {
        String workerId = task.getWorkerId();
        String workerflowInstanceId = task.getWorkflowInstanceId();
        TaskResult result = new TaskResult(task);

        System.out.println("Input:" + task.getInputData());

        result.getOutputData().put("name", "test");
        result.getOutputData().put("id", "1145141919810");
        result.getOutputData().put("age", 1551);
        result.getOutputData().put("level", "senior");
        result.getOutputData().put("score", "98");
        result.getOutputData().put("result", "status ok");
        result.getOutputData().put("output", "this is an output");


        try {
            Thread.sleep(KyaputenClientConfig.getSleepTime());
        }
        catch (InterruptedException e) {
            System.err.println("Thread InterruptedException");
        }

        result.setStatus(Status.COMPLETED);
        result.setWorkerId(workerId);
        result.setWorkflowInstanceId(workerflowInstanceId);

        return result;
    }
}
