package com.kagaya.kyaputen.common.schedule;

import java.util.HashMap;
import java.util.Map;

public class DeploymentPlan {

    private static Map<String, PodResource> deploymentPlanMap = new HashMap<>();

    public PodResource getPlan(String taskName) {

        return deploymentPlanMap.get(taskName);
    }

    public void setPlan(String taskName, PodResource resource) {

        deploymentPlanMap.put(taskName, resource);
    }

}
