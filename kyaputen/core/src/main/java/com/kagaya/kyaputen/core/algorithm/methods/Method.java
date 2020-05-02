package com.kagaya.kyaputen.core.algorithm.methods;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;

/**
 * 算法接口
 */
public interface Method {

    ExecutionPlan schedule(long startTime);
}
