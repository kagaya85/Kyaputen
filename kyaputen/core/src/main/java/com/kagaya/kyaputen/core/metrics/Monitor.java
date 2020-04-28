package com.kagaya.kyaputen.core.metrics;

import java.util.HashMap;
import java.util.Map;

public class Monitor {

    private static Map<String, Long> taskExecutionTimeMap = new HashMap<>();

    private static Map<String, Integer> taskExecutionLogNumMap = new HashMap<>();

    private static Map<String, Long> nodeLatencyTimeMap = new HashMap<>();

    /**
     * 记录任务执行时间的平均值
     * @param taskType 任务类型
     * @param time 任务执行时间
     */
    public static void logTaskExecutionTime(String taskType, long time) {
        Long t = taskExecutionTimeMap.get(taskType);

        if (t == null) {
            t = time;
            taskExecutionLogNumMap.put(taskType, 1);
        } else {
            int num = taskExecutionLogNumMap.get(taskType);
            t = t * num;
            num++;
            t = (t + time) / num;
            taskExecutionLogNumMap.put(taskType, num);
        }

        taskExecutionTimeMap.put(taskType, t);
    }

    public static long getTaskExecutionTime(String taskType) {
        Long time = taskExecutionTimeMap.get(taskType);

        if (time == null) {
            time = 0L;
        }

        return time;
    }

    public static void resetTaskExecutionTime(String taskType) {
        taskExecutionTimeMap.remove(taskType);
    }

    /**
     * 记录node节点网络延时的最新值
     * @param nodeId Id
     * @param time 延时时长
     */
    public static void logNodeLatencyTime(String nodeId, long time) {
        nodeLatencyTimeMap.put(nodeId, time);
    }

    public static long getNodeLatencyTime(String nodeId) {
        Long time = nodeLatencyTimeMap.get(nodeId);

        if (time == null) {
            time = -1L;
        }

        return time;
    }
}
