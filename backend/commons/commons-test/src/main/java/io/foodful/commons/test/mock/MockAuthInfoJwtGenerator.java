package io.foodful.commons.test.mock;

import io.foodful.commons.core.dto.AuthInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MockAuthInfoJwtGenerator {

    @Value("${foodful.auth.user-data-header}")
    private String tokenHeaderName;

    @Value("${foodful.auth.jwt.key}")
    private String jwtKey;

    public String getMockJwt(AuthInfo authInfo){
        return this.getMockJwtWithUserRole(authInfo);
    }

    public String getMockJwtWithUserRole(AuthInfo authInfo){
        return Jwts.builder()
                .setClaims(getMockClaims(authInfo.userId, "USER"))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public String getMockJwtWithAdminRole(AuthInfo authInfo){
        return Jwts.builder()
                .setClaims(getMockClaims(authInfo.userId,"ADMIN"))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public String getHeaderName(){
        return tokenHeaderName;
    }

    private Map<String, Object> getMockClaims(String userId, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("ROLES", role);
        return claims;
    }

}