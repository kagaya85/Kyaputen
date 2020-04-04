package com.kagaya.kyaputen.client.config;

public class KyaputenClientConfig {

    private static String address = "localhost";

    private static int port = 18080;

    private static String domain = null;

    private static int pollingInterval = 1000;

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
}
