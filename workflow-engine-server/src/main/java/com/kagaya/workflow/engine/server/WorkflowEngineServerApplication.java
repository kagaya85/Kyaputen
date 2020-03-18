package com.kagaya.workflow.engine.server;

import com.kagaya.kyaputen.server.KyaputenServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
public class WorkflowEngineServerApplication {

	@Value("${server.port}")
	private static int port;

	public static void main(String[] args) {
		KyaputenServer server = new KyaputenServer(port);

		try {
			server.start();
		}
		catch (IOException ioe) {
			ioe.printStackTrace(System.err);
			server.stop();
		}

		try {
			server.blockUntilShutdown();
		}
		catch (InterruptedException ie) {
			ie.printStackTrace(System.err);
			server.stop();
		}

		SpringApplication.run(WorkflowEngineServerApplication.class, args);
	}

}
