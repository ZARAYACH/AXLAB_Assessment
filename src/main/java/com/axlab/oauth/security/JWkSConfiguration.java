package com.axlab.oauth.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JWkSConfiguration {

    private final RSAKeyPairService rsaKeyPairService;

    @Bean
    public JWKSet jwkSet() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaKeyPairService.getKeyPair();
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(rsaKeyPairService.getKeyPairKeyId());
        return new JWKSet(builder.build());
    }
}
