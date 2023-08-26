package com.example.apipersona.client.newToken;

import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ClientTokenManager {
//  Nombre que le colocamos al cliente en el .yml
    public final String CLIENT_REGISTRATION_ID = "keycloak";
    private final ClientRegistrationRepository clientRegistrationRepository;
//  Inyectamos el manager configurado previamente
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public ClientTokenManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    public String getToken() {
        try {
//          obtener el cliente que colocamos en el yml, lo obtenemos por el nombre que le colocamos (keycloak)
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(CLIENT_REGISTRATION_ID);

            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
//                  Nombre que le colocamos en el .yml al cliente, en este caso "keycloak"
//                  no es necesario usar la clase ClientRegistration podríamos colocarlo manualmente
                    .withClientRegistrationId(clientRegistration.getRegistrationId())
//                  Client id del cliente en keycloak, en este caso "backend"
//                  no es necesario usar la clase ClientRegistration podríamos colocarlo manualmente
                    .principal(clientRegistration.getClientId())
                    .build();

            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);

            if (isNull(client)) {
                throw new IllegalStateException("Error al obtener el access token");
            }

            System.out.println("Token: " + client.getAccessToken().getTokenValue());
            return client.getAccessToken().getTokenValue();

        }catch (Exception ex) {
            System.out.println("Error obteniendo el access token");
            ex.printStackTrace();
        }
        return null;
    }
}
