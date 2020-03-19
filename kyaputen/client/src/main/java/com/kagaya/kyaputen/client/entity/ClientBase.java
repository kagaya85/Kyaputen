package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.config.DefaultKyaputenClientConfig;
import com.kagaya.kyaputen.client.exceptions.KyaputenClientException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.net.URI;


public abstract class ClientBase {

    public static Logger logger = LoggerFactory.getLogger(ClientBase.class);

    protected String root = "";

    protected KyaputenClientConfig kyaputenClientConfig;

    protected ClientBase () {
        this(new DefaultKyaputenClientConfig());
    }

    protected ClientBase(KyaputenClientConfig config) {
        kyaputenClientConfig = config;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    private void handleRuntimeException(RuntimeException exception, URI uri) {
        String errorMessage = String.format("Unable to invoke Kyaputen API with uri: %s, runtime exception occured", uri);
        logger.error(errorMessage, exception);
        throw new KyaputenClientException(errorMessage, exception);
    }

}
