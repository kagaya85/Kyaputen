package com.kagaya.kyaputen.client.config;

public class DefaultKyaputenClientConfig implements KyaputenClientConfig {

    private String address = "127.0.0.1";

    private int port = 18080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
