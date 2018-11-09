package io.foodful.auth.core.service;

import io.foodful.user.api.UserClient;
import io.foodful.user.api.dto.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InternalTokensService {

    private UserClient userClient;

    private String jwtKey;

    public InternalTokensService(UserClient userClient, @Value("${foodful.auth.jwt.key}") String jwtKey) {
        this.userClient = userClient;
        this.jwtKey = jwtKey;
    }

    public String createJwtForUser(String userId) {
        log.info("Creating internal jwt for user: {}", userId);
        UserResponse profile = userClient.getUser(userId);

        return Jwts.builder()
                .setClaims(getCommonUserClaims(profile))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    private Map<String, Object> getCommonUserClaims(UserResponse profile) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", profile.userId);
        claims.put("ROLES", profile.roles.stream().reduce((a, b) -> a + "," + b).orElse(""));
        return claims;
    }

}
