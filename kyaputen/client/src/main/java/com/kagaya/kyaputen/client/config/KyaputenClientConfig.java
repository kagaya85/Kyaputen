package com.kagaya.kyaputen.client.config;

public interface KyaputenClientConfig {

    String address = "";

    int port = 0;

    int getPort();

    void setPort(int port);

    String getAddress();

    void setAddress(String address);
}
