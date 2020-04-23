package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

public class Scheduler implements SchedulerBase {

    public Scheduler() {

    }

    /**
     * 计算工作流中每个任务的资源需求量
     * @param workflowDef - 工作流定义
     * @param deadlineFactor - 最后期限管理严格成都
     */
    @Override
    public void calcResource(WorkflowDefinition workflowDef, double deadlineFactor) {

    }

    /**
     *
     * @param vmType
     * @param ecu
     * @param taskSize
     * @return
     */
    private double calcTaskCost(String vmType, double ecu, double taskSize) {

        return 0.0;
    }

    private double calcWorkflowCost() {
        double cost = 0;

        return cost;
    }


}