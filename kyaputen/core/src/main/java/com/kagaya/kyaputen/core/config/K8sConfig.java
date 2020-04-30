package com.kagaya.kyaputen.core.config;

public class K8sConfig {
    
    private static String apiServerAddress = "";
    private static String token;

    /**
     * @return the apiServerAddress
     */
    public static String getApiServerAddress() {
        return apiServerAddress;
    }

    /**
     * @param apiServerAddress the apiServerAddress to set
     */
    public static void setApiServerAddress(String apiServerAddress) {
        K8sConfig.apiServerAddress = apiServerAddress;
    }

    /**
     * @return the token
     */
    public static String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public static void setToken(String token) {
        K8sConfig.token = token;
    }
}