package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;
import com.kagaya.kyaputen.core.config.K8sConfig;
import com.kagaya.kyaputen.core.dao.NodeResourceDAO;
import com.kagaya.kyaputen.core.dao.PodResourceDAO;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class KubernetesService {

    private static final Logger logger = LoggerFactory.getLogger(KubernetesService.class);

    private final NodeResourceDAO nodeResourceDAO = new NodeResourceDAO();

    private final PodResourceDAO podResourceDAO = new PodResourceDAO();

    public KubernetesService(String apiServerAddress, String token) {
        ApiClient client = new ClientBuilder().setBasePath(apiServerAddress).setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(token)).build();

        Configuration.setDefaultApiClient(client);
    }

    public KubernetesService() {
        ApiClient client = new ClientBuilder().setBasePath(K8sConfig.getApiServerAddress()).setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(K8sConfig.getToken())).build();

        Configuration.setDefaultApiClient(client);
    }

    public Boolean createPod(Pod pod, TaskDefinition taskDef, TaskExecutionPlan plan) {

        String namespace = null;
        V1Pod body = new V1Pod();
        V1PodSpec podSpec = new V1PodSpec();
        V1ObjectMeta metadata = new V1ObjectMeta();
        V1Container container = new V1Container();
        V1ResourceRequirements resources = new V1ResourceRequirements();
        List<V1Container> containers = new LinkedList<>();
        Map<String, Quantity> resourceMap = new HashMap<>();
        
        Node node = nodeResourceDAO.getNode(pod.getNodeId());

        container.setName(plan.getTaskName());
        container.setImage(pod.getTaskImageName());

        resources.limits()
        container.setResources(resources);

        containers.add(container);

        podSpec.setContainers(containers);
        podSpec.setNodeName(node.getNodeName());

        metadata.setName(pod.getPodId());
        body.setSpec(podSpec);
        body.setMetadata(metadata);

        CoreV1Api apiInstance = new CoreV1Api();

        try {
            V1Pod result =  apiInstance.createNamespacedPod(namespace, body,true, "true", null);

            logger.debug(result.toString());
            pod.setStatus(Pod.PodStatus.IDLE);
            return true;
        } catch (ApiException e) {
            logger.error("Create pod error in namespace: {} for reason: {}, header: {}", namespace, e.getResponseBody(), e.getResponseHeaders());
            e.printStackTrace();

            return false;
        }
    }

    public Pod resizePod(Pod pod, TaskDefinition taskDef) {

        pod.setStatus(Pod.PodStatus.IDLE);
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


    public Node startNode(Node node) {

        CoreV1Api apiInstance = new CoreV1Api();


        return new Node();
    }

    public void shutdownNode(Node node) {

    }
}
