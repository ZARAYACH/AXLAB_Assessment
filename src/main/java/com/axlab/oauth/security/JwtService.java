package com.axlab.oauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class JwtService {
    private final long jwtExpiration;
    private final Algorithm rsa256;
    private final JWTVerifier verifier;
    private final RSAKeyPairService rsaKeyPairService;

    public JwtService(@Value("${security.jwt.expiration-time}") long jwtExpiration, RSAKeyPairService rsaKeyPairService) throws NoSuchAlgorithmException {
        this.jwtExpiration = jwtExpiration;
        this.rsaKeyPairService = rsaKeyPairService;
        this.rsa256 = Algorithm.RSA256((RSAPublicKey) rsaKeyPairService.getKeyPair().getPublic(), (RSAPrivateKey) rsaKeyPairService.getKeyPair().getPrivate());
        this.verifier = JWT.require(rsa256).build();
    }

    public Optional<String> validateTokenAndGetUsername(final String token) {
        try {
            return Optional.of(verifier.verify(token).getSubject());
        } catch (final JWTVerificationException verificationEx) {
            log.warn("token invalid: {}", verificationEx.getMessage());
            return Optional.empty();
        }
    }

    public String generateToken(UserDetails userDetails) throws NoSuchAlgorithmException {
        return buildToken(userDetails, jwtExpiration);
    }

    private String buildToken(
            UserDetails userDetails,
            long expiration
    ) throws NoSuchAlgorithmException {
        final Instant now = Instant.now();
        return JWT.create()
                .withKeyId(rsaKeyPairService.getKeyPairKeyId())
                .withSubject(userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expiration))
                .sign(rsa256);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = verifier.verify(token).getSubject();
        return (username.equals(userDetails.getUsername()));
    }


}