package com.axlab.oauth;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwkSetController {

    private final JWKSet jwkSet;

    @GetMapping("/.well-known/jwks")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
}
