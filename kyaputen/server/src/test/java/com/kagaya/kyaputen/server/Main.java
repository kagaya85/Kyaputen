package com.kagaya.kyaputen.server;

import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.core.dao.NodeResourceDAO;
import com.kagaya.kyaputen.core.service.ExecutionService;
import com.kagaya.kyaputen.core.utils.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        KyaputenServer server = new KyaputenServer(18080, "/Users/kagaya/Documents/repos/microservice/config/workflow-test.json");

        ExecutionService executionService = new ExecutionService();
        NodeResourceDAO nodeResourceDAO = new NodeResourceDAO();

        Node testNode = new Node();

        testNode.setId(IdGenerator.generate());
        testNode.setNodeName("testNode");
        testNode.setTotalComputeUnit(4);
        testNode.setPrice(8);
        testNode.setAllocatedComputeUnit(0);
        testNode.setStatus(Node.NodeStatus.Idle);

        nodeResourceDAO.addNode(testNode);

        Map<String, Object> input = new HashMap<>();

        input.put("name", "kagaya");
        input.put("id", "1234567");
        input.put("age", 321);
        input.put("test", "321test123");

        executionService.startWorkflow("workflow-test", input);


        server.start();
    }

}
