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

	public static void main(String[] args) {
		SpringApplication.run(WorkflowEngineServerApplication.class, args);
	}

}
