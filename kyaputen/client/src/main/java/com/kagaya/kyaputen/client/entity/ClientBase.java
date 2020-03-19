package com.kagaya.kyaputen.client.utility;

import com.kagaya.kyaputen.client.config.ClientConfig;
import com.kagaya.kyaputen.client.config.DefaultClientConfig;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.net.URI;


public abstract class ClientBase {

    public static Logger logger = LoggerFactory.getLogger(ClientBase.class);

    protected String root = "";

    protected ClientConfig clientConfig;

    protected ClientBase () {
        this(new DefaultClientConfig());
    }

    protected ClientBase(ClientConfig config) {
        clientConfig = config;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    private handleRuntimeException(RuntimeException exception, URI uri) {
        
    }

}
