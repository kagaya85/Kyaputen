package com.kagaya.kyaputen.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class KyaputenClientConfig {

    private static String taskType = "test";

    private static String address = "localhost";

    private static int port = 18080;

    private static String domain = null;

    private static int pollingInterval = 1000;

    private static long sleepTime = 1000;

    public KyaputenClientConfig() {

    }

    public KyaputenClientConfig(String configPath) {

        Gson gson = new GsonBuilder().create();

        try {
            JsonReader reader = new JsonReader(new FileReader(configPath));
            ConfigBean config = gson.fromJson(reader, ConfigBean.class);

            if (config.taskType != null)
                taskType = config.taskType;

            if (config.address != null)
                address = config.address;

            if (config.port != null)
                port = config.port;

            if (config.pollingInterval != null)
                pollingInterval = config.pollingInterval;

            if (config.sleepTime != null)
                sleepTime = config.sleepTime;

        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static String getTaskType() {
        return taskType;
    }

    public static void setTaskType(String taskType) {
        KyaputenClientConfig.taskType = taskType;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int newPort) {
        KyaputenClientConfig.port = newPort;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String newAddress) {
        KyaputenClientConfig.address = newAddress;
    }

    public static String getDomain() {
        return domain;
    }

    public static void setDomain(String newDomain) {
        KyaputenClientConfig.domain = newDomain;
    }

    public static int getPollingInterval() {
        return pollingInterval;
    }

    public static void setPollingInterval(int pollingInterval) {
        KyaputenClientConfig.pollingInterval = pollingInterval;
    }

    public static long getSleepTime() {
        return sleepTime;
    }

    public static void setSleepTime(long sleepTime) {
        KyaputenClientConfig.sleepTime = sleepTime;
    }
}
