package com.kagaya.kyaputen.client.worker;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;

public class PropertyFactory {
    private static HashMap<String, String> properties = new HashMap<>();

    public PropertyFactory() {
        // 属性初始化
    }

    public static String getString(String property, String defaultValue) {
        return properties.getOrDefault(property, defaultValue);
    }

    public static Integer getInteger(String property, Integer defaultValue) {
        return Integer.parseInt(properties.getOrDefault(property, defaultValue.toString()));
    }
    public static Boolean getBoolean(String property, Boolean defaultValue) {
        return Boolean.parseBoolean(properties.getOrDefault(property, defaultValue.toString()));
    }



}
