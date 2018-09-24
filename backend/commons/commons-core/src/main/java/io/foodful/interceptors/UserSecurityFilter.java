package io.foodful.interceptors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserSecurityFilter extends GenericFilterBean {

    private String userDataHeaderName;

    private String jwtKey;

    public UserSecurityFilter(String userDataHeaderName, String jwtKey) {
        this.userDataHeaderName = userDataHeaderName;
        this.jwtKey = jwtKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userInfoHeader = request.getHeader(userDataHeaderName);
        if(userInfoHeader != null && !userInfoHeader.isEmpty()) {
            Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(userInfoHeader).getBody();
            User principal = new User(claims.getSubject(), "", this.getAuthoritiesFromJwt(claims));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, this.getAuthoritiesFromJwt(claims));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private List<SimpleGrantedAuthority> getAuthoritiesFromJwt(Claims claims){
        String roles = claims.get("ROLES", String.class);
        if(roles != null) {
            return Arrays.stream(roles.split(","))
                    .map(role -> "ROLE_" + role)
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}