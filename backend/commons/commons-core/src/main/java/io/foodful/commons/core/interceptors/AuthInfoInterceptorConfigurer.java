package io.foodful.commons.core.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInfoInterceptorConfigurer implements WebMvcConfigurer {

    @Value("${foodful.auth.user-data-header}")
    private String userDataHeaderName;

    @Value("${foodful.auth.jwt.key}")
    private String jwtKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInfoInterceptor(userDataHeaderName, jwtKey));
    }

}