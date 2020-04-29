package com.kagaya.kyaputen.core.config;

import java.util.HashMap;
import java.util.Map;

public class Price {

    // 单位cu的价格表
    private static Map<String, Double> nodePriceMap = new HashMap<>();

    public static void setNodePrice(String nodeType, double price) {
        nodePriceMap.put(nodeType, price);
    }

    /**
     * 获取单位cu的价格
     * @param nodeType
     * @return
     */
    public static double getNodeUnitPrice(String nodeType) {
        if (nodeType.equals("Test_Node"))
            return 0.015;

        return nodePriceMap.get(nodeType);
    }
}
