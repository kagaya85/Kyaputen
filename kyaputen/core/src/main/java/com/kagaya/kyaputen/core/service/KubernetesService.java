package com.kagaya.kyaputen.core.service;

import com.google.gson.Gson;
import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Node;
import io.kubernetes.client.models.V1Pod;
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

    public Object createPod(V1Pod pod, String nameSpace, String dryRun) {

        String pretty = "ture";

        CoreV1Api apiInstance = new CoreV1Api();

        try {
            Object result = apiInstance.createNamespacedPod(nameSpace, pod,true, pretty, dryRun);
            // JSON
            Gson gson= new Gson();
            String s = gson.toJson(result);

        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#listClusterCustomObject");
            e.printStackTrace();
        }

    }

    public Node createNode() {

        return new Node();
    }

    public void deleteNode(String nodeId) {

    }
}
