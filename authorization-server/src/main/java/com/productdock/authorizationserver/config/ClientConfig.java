package com.productdock.authorizationserver.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Configuration
public class ClientConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    ApplicationRunner clientsRunner(RegisteredClientRepository repository) {
        return args -> {
            var clientId = "productdock";
            if (repository.findByClientId(clientId) == null) {
                repository.save(
                        RegisteredClient.withId(UUID.randomUUID().toString())
                                .clientId("productdock")
                                .clientSecret("{bcrypt}$2a$10$0lAOvcI202G9go0h7MD75OczUgjNb4cp2KlPsH13NFqQ0NtipN7dq")
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                                .redirectUri("http://127.0.0.1/login/oauth2/code/productdock")
                                .scope(OidcScopes.OPENID)
                                .scope(OidcScopes.PROFILE)
                                .scope(OidcScopes.EMAIL)
                                .scope(OidcScopes.PHONE)
                                .scope(OidcScopes.ADDRESS)
                                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                                .build()
                );
            }
        };
    }
}