package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDataDAO {

    //  时间单位ms
    private final static long timeIntervalMs = 1000;

    // 按一定时间间隔存储任务执行计划
    public static Map<Long, List<TaskExecutionPlan>> ExecutionPlanMap = new HashMap<>();

    /**
     * 判断某节点是否有空闲时间
     * @param startTime
     * @param timeLimit
     * @return true or false
     */
    public static boolean isNodeSpare(long startTime, long timeLimit) {

        long st = startTime / timeIntervalMs;
        long tl = timeLimit / timeIntervalMs;

        return false;
    }

    public static void setTaskExecutionPlan(TaskExecutionPlan plan) {
        // 清除过时数据

        // 加入新计划
    }
}
