package com.kagaya.kyaputen.client.config;

public class KyaputenClientConfig {

    private String address = "localhost";

    private int port = 18080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
