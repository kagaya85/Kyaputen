package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

import java.util.List;

public class KubernetesService {

    private ApiClient client;

    public KubernetesService(String apiServerAddress, String token) {
        client = new ClientBuilder().setBasePath(apiServerAddress).setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(token)).build();

        Configuration.setDefaultApiClient(client);
    }

    public Pod createPod(String taskName, String nodeId) {

        return new Pod();
    }

    public Pod resizePod(String podId, int cpu, int mem) {

        return new Pod();
    }

    public void deletePod(String podId) {

    }

    public List<Pod> getPodsOnNode(String nodeId) {

    }

    public Node createNode() {

        return new Node();
    }

    public void deleteNode(String nodeId) {

    }
}
