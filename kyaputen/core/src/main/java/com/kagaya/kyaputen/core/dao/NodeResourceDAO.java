package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeResourceDAO {

    // 管理已有node信息
    private static Map<String, Node> nodeMap = new HashMap<>();

    public NodeResourceDAO() {

    }

    public static Node getNode(String nodeId) {
        return nodeMap.get(nodeId);
    }

    public static List<Node> getNodeList() {
        return new ArrayList<>(nodeMap.values());
    }

}
