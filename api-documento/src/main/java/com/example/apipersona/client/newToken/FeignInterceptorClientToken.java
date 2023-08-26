package com.example.apipersona.client.newToken;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

//@Component
public class FeignInterceptorClientToken implements RequestInterceptor {
    private final ClientTokenManager clientTokenManager;

    public FeignInterceptorClientToken(ClientTokenManager clientTokenManager) {
        this.clientTokenManager = clientTokenManager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("New access token");
        String token = clientTokenManager.getToken();
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
