package io.foodful.commons.core.interceptors;

import io.foodful.commons.core.dto.AuthInfo;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInfoInterceptor implements HandlerInterceptor {

    private String userDataHeaderName;

    private String jwtKey;

    public AuthInfoInterceptor(String userDataHeaderName, String jwtKey) {
        this.userDataHeaderName = userDataHeaderName;
        this.jwtKey = jwtKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userInfoHeader = request.getHeader(userDataHeaderName);
        if(userInfoHeader != null && !userInfoHeader.isEmpty()) {
            String userId = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(userInfoHeader).getBody().getSubject();
            request.setAttribute("authInfo", this.createAuthInfo(userId));
        }

        return true;
    }

    private AuthInfo createAuthInfo(String userId){
        AuthInfo info = new AuthInfo();
        info.userId = userId;
        return info;
    }
}