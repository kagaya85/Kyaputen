package com.kagaya.kyaputen.core.service;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

public class KubernetesService {

    private ApiClient client;

    public KubernetesService(String apiServerAddress, String token) {
        client = new ClientBuilder().setBasePath(apiServerAddress).setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(token)).build();

        Configuration.setDefaultApiClient(client);
    }

}
