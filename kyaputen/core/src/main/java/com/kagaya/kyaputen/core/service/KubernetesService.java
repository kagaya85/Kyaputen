package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class KubernetesService {

    private static final Logger logger = LoggerFactory.getLogger(KubernetesService.class);

    public KubernetesService(String apiServerAddress, String token) {
        ApiClient client = new ClientBuilder().setBasePath(apiServerAddress).setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(token)).build();

        Configuration.setDefaultApiClient(client);
    }

    public Boolean createPod(V1Pod pod, String namespace) {

        CoreV1Api apiInstance = new CoreV1Api();

        try {
            V1Pod result =  apiInstance.createNamespacedPod(namespace, pod,true, "true", null);

            logger.debug(result.toString());
            return true;
        } catch (ApiException e) {
            logger.error("Create pod error in namespace: {} for reason: {}, header: {}", namespace, e.getResponseBody(), e.getResponseHeaders());
            e.printStackTrace();

            return false;
        }
    }

    public Pod resizePod(String podName, String namespace, V1ResourceRequirements resource) {

        return new Pod();
    }

    public void deletePod(String podName, String namespace) {

        CoreV1Api apiInstance = new CoreV1Api();

        Integer gracePeriodSeconds = 56;
        Boolean orphanDependents = true;
        String propagationPolicy = "Background";
        V1DeleteOptions body = new V1DeleteOptions();

        try{
            V1Status result = apiInstance.deleteNamespacedPod(podName, namespace, "true", body, null, gracePeriodSeconds, orphanDependents, propagationPolicy);
            logger.debug(result.toString());
        } catch (ApiException e) {
            logger.error("Delete pod error in namespace: {} for reason: {}, header: {}", namespace, e.getResponseBody(), e.getResponseHeaders());

            e.printStackTrace();
        }

    }

    public List<Pod> getNamespacePods(String nodeName, String namespace) {

        CoreV1Api apiInstance = new CoreV1Api();

        Boolean includeUniinitialized = false;
        String pretty = "true";
        String _continue = null;
        String fieldSelector = null;
        String labelSelector = null;
        Integer limit = 128;
        String resourceVersion = null;
        Integer timeoutSeconds = 60;
        Boolean watch = false;

        try {
            V1PodList result = apiInstance.listNamespacedPod(namespace, includeUniinitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            logger.debug(result.toString());

            // convert from V1Pod to Pod
        } catch (ApiException e) {
            logger.error("List pod error in namespace: {} for reason: {}, header: {}", namespace, e.getResponseBody(), e.getResponseHeaders());
            e.printStackTrace();
        }

        ///////////////
        return new LinkedList<Pod>();
    }


    public Node createNode(V1Node node, String dryRun) {

        CoreV1Api apiInstance = new CoreV1Api();


        return new Node();
    }

    public void deleteNode(String nodeId) {

    }
}
