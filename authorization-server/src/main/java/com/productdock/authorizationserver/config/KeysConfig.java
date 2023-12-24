package com.productdock.authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class KeysConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource(
            @Value("${jwk.key.id}") String id,
            @Value("${jwk.key.private}") RSAPrivateKey privateKey,
            @Value("${jwk.key.public}") RSAPublicKey publicKey
    ) {
        var rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(id).build();
        var jwk = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwk);

    }

}