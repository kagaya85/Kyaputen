package com.kagaya.workflow.engine.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineController {

    @Value("${server.port}")
    private String port;

}
