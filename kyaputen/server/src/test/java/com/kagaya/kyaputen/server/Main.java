package com.kagaya.kyaputen.server;

public class Main {

    public static void main(String[] args) {

        KyaputenServer server = new KyaputenServer(18080);

        server.start();
    }

}
