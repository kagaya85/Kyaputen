package com.kagaya.kyaputen.core.metadata;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;

import java.util.HashMap;
import java.util.Map;

public class ResourceMetadata {

    // 管理已有node信息
    private static Map<String, Node> nodeMap = new HashMap<>();

    // 管理已有pod信息
    private static Map<String, Pod> podMap = new HashMap<>();

    public ResourceMetadata() {

    }

    public Node getNode(String nodeId) {


    }
}
