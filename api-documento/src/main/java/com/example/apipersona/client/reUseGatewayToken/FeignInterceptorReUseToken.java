package com.example.apipersona.client.reUseGatewayToken;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

// Reenvia el access token del usuario que recibe del gateway
@Component
public class FeignInterceptorReUseToken implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("Reenvío access token");
        String token = getAccessToken();
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }

//  Simplemente obtenemos el token del contexto de seguridad
    private String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;
        if (authentication != null) {
            try {
                token = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
            } catch (Exception exception) {
                System.out.println("Error al obtener el token del SecurityContextHolder");
                exception.printStackTrace();
            }
        }
        return token;
    }
}