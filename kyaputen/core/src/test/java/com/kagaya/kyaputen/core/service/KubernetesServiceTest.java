package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.core.service.KubernetesService;
import com.kagaya.kyaputen.core.utils.IdGenerator;

public class KubernetesServiceTest {

    public static void main(String[] args) {
        String apiServiceAddress = "http://127.0.0.1:8080";
        String token = "";

        KubernetesService k8s = new KubernetesService(apiServiceAddress, token);

        Pod pod = new Pod();
        Node node = new Node();

        String podId = IdGenerator.generate();
        double ce = 1;

        node.setNodeName("minikube");

        pod.setTaskImageName("ubuntu");
        pod.setNodeName("minikube");
        pod.setPodId(podId);

        k8s.createPod(pod, ce);

    }
}
