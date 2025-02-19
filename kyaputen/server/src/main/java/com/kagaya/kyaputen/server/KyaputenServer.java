package com.kagaya.kyaputen.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.core.dao.NodeResourceDAO;
import com.kagaya.kyaputen.core.dao.WorkflowDefinitionDAO;
import com.kagaya.kyaputen.server.grpc.GRPCServer;
import com.kagaya.kyaputen.server.grpc.GRPCServerBuilder;
import com.kagaya.kyaputen.server.metadata.MetadataService;
import com.kagaya.kyaputen.server.metadata.NodeBean;
import com.kagaya.kyaputen.server.module.ServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KyaputenServer {

    private static final Logger logger = LoggerFactory.getLogger(KyaputenServer.class);

    private final GRPCServer grpcServer;

    private final GRPCServerBuilder grpcServerBuilder;

    private final MetadataService metadataService = new MetadataService();

    private final WorkflowDefinitionDAO workflowDefinitionDAO = new WorkflowDefinitionDAO();

    private final NodeResourceDAO nodeResourceDAO = new NodeResourceDAO();

    public KyaputenServer(int port) {

        Injector injector = Guice.createInjector(new ServerModule());

        grpcServerBuilder = injector.getInstance(GRPCServerBuilder.class);

        grpcServer = grpcServerBuilder.build();

    }


    public KyaputenServer(int port, String workflowDefinitionPath, String nodeConfigPath) {

        Injector injector = Guice.createInjector(new ServerModule());

        grpcServerBuilder = injector.getInstance(GRPCServerBuilder.class);

        grpcServer = grpcServerBuilder.build();

        List<WorkflowDefinition> wds = metadataService.readWorkflowConfig(workflowDefinitionPath);

        logger.debug("Read metadata from: {}, workflow number: {}", workflowDefinitionPath, wds.size());

        List<Node> nodes = metadataService.readNodeConfig(nodeConfigPath);

        logger.debug("Read metadata from: {}, node number: {}", nodeConfigPath, nodes.size());

        workflowDefinitionDAO.addAll(wds);
        nodeResourceDAO.addAll(nodes);

        logger.debug("{} workflow added", wds.size());
    }

    public void start() {
        try {
            grpcServer.start();
            logger.info("gRPC server started");
            grpcServer.blockUntilShutdown();
        }
        catch (Exception e) {
            logger.error("gRPC server error occured: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(3);
        }
    }

//    private class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {
//
//        @Override
//        public void poll(PollRequest request, StreamObserver<PollResponse> responseObserver) {
//            System.out.println(("Request Task Type: ") + request.getTaskType());
//            System.out.println("Request Domain: " + request.getDomain());
//            System.out.println(("Request Worker Id: ") + request.getWorkerId());
//
//            Task task = Task.newBuilder().setStartTime(System.currentTimeMillis()/1000L).build();
//            PollResponse response = PollResponse.newBuilder().setTask(task).build();
//
//            responseObserver.onNext(response);
//            responseObserver.onCompleted();
//        }
//    }

}
