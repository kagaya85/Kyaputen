package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeResourceDAO {

    // 管理已有node信息 nodeName -> Node
    private static Map<String, Node> nodeMap = new HashMap<>();

    public NodeResourceDAO() {

    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        NodeResourceDAO.nodeMap = nodeMap;
    }

    public Node getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public List<Node> getNodeList() {
        return new ArrayList<>(nodeMap.values());
    }

    public void addNode(Node node) {
        nodeMap.put(node.getNodeName(), node);
    }

}
