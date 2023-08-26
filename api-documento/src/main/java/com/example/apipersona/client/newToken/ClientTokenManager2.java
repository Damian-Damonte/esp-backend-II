package com.example.apipersona.client.newToken;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

// ClientTokenManager más sencillo, no inyectamos la clase clientRegistrationRepository, sino que colocamos
// el CLIENT_REGISTRATION_ID y el CLIENT_ID directamente en constantes

//@Component
public class ClientTokenManager2 {
    public final String CLIENT_REGISTRATION_ID = "keycloak";
    private final String CLIENT_ID = "backend";
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public ClientTokenManager2(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    public String getToken() {
        try {
            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                    .principal(CLIENT_ID)
                    .build();

            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);

            if (isNull(client)) {
                throw new IllegalStateException("Error al obtener el access token");
            }
            return client.getAccessToken().getTokenValue();

        }catch (Exception ex) {
            System.out.println("Error obteniendo el access token");
            ex.printStackTrace();
        }
        return null;
    }
}
