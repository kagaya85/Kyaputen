package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.core.service.KubernetesService;

public class KubernetesServiceTest {

    public static void main(String[] args) {
        String apiServiceAddress = "localhost";
        String token = "";

        KubernetesService k8s = new KubernetesService(apiServiceAddress, token);


    }
}
