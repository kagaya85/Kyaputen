package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.runner.TaskRunner;
import com.kagaya.kyaputen.client.worker.Worker;


public class Main {
    public static void main(String[] args) {
        KyaputenClientConfig.setAddress("localhost");
        KyaputenClientConfig.setPort(18080);

        TaskClient taskClient = new TaskClient();

//        Task task = taskClient.pollTask("testType", "test123", "testDomain");
//        System.out.println("task status: " + task.getStatus().name());

        Worker worker = new SampleWorker("testTask");

        TaskRunner runner = new TaskRunner.Builder(taskClient, worker).build();

        try {
            runner.start();
        } catch (Exception e) {
            runner.shutdown();
        }
    }
}
