package com.example.apipersona.client.newToken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class OAuthClientManagerConfig {
//  Es una instancia de OAuth2AuthorizedClientService que se utiliza para almacenar y recuperar tokens de acceso
//  autorizados en la aplicación. Un OAuth2AuthorizedClientService se encarga de la persistencia y gestión de los
//  tokens de acceso
//  se enfoca en el almacenamiento y la administración de tokens de acceso
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

//  Es una instancia de ClientRegistrationRepository que almacena información sobre los clientes registrados en
//  OAuth2, como sus identificaciones (client-id), secretos (client-secret), URI de autorización, URI de token,
//  entre otros detalles.
//  Se encarga de obtener los clientes configurados en el .yml
    private final ClientRegistrationRepository clientRegistrationRepository;

    public OAuthClientManagerConfig(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, ClientRegistrationRepository clientRegistrationRepository) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

//  Este objeto tiene el método authorize() el cual realizará una petición de autenticación a Keyclaok, este devolverá un access token
    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager() {
//      determinar si es necesario proporcionar un token nuevo o si se puede reutilizar un token existente
//      define cómo se gestionan y obtienen los tokens de acceso autorizados en el contexto de autenticación y autorización basadas en OAuth2
//      se enfoca en determinar cuándo y cómo se deben obtener y utilizar esos tokens en el proceso de autenticación
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, oAuth2AuthorizedClientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
