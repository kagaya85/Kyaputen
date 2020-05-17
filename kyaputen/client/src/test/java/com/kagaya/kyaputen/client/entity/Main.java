package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.runner.TaskRunner;
import com.kagaya.kyaputen.client.worker.Worker;


public class Main {
    public static void main(String[] args) {

        KyaputenClientConfig config = new KyaputenClientConfig("./config.json");

        TaskClient taskClient = new TaskClient(config);

//        Task task = taskClient.pollTask("testType", "test123", "testDomain");
//        System.out.println("task status: " + task.getStatus().name());

        Worker worker = new SampleWorker(KyaputenClientConfig.getTaskType());

        TaskRunner runner = new TaskRunner.Builder(taskClient, worker).build();

        try {
            runner.start();
        } catch (Exception e) {
            runner.shutdown();
        }
    }
}
