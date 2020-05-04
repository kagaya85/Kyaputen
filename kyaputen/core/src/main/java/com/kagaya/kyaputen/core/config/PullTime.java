package com.kagaya.kyaputen.core.config;

import java.util.HashMap;
import java.util.Map;

public class PullTime {

    private static Map<String, Long> pullTimeMap = new HashMap<>();

    public static void addPulllTime(String imageName, long time) {
        pullTimeMap.put(imageName, time);
    }

    public static long getPullTime(String imageName) {
        Long time = pullTimeMap.get(imageName);

        if (time == null) {
            time = 1000L;   // 默认值
        }

        return time;
    }

}
