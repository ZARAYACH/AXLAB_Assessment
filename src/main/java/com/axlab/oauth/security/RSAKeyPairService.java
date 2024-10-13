package com.axlab.oauth.security;

import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class RSAKeyPairService {

    private KeyPairWithId keyPair;

    private KeyPairWithId generateKeyPair() throws NoSuchAlgorithmException {
        // This Should never be used in production
        // To use in prod Please import the key pair from somewhere secure and use a key pair rotation system for more robust security and never share the private keys
        if (keyPair == null) {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            keyPair = new KeyPairWithId(UUID.randomUUID().toString(), kpg.genKeyPair());
        }
        return keyPair;
    }

    public String getKeyPairKeyId() throws NoSuchAlgorithmException {
        return this.generateKeyPair().keyId;
    }

    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        return this.generateKeyPair().keyPair();
    }

    record KeyPairWithId(
            String keyId,
            KeyPair keyPair
    ) {
    }
}
