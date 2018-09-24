package io.foodful.feign.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderForwarderInterceptorsConfigurer {

    @Value("${foodful.auth.user-data-header}")
    private String userDataHeaderName;

    @Bean(name = "authInfoForwarderInterceptor")
    public HeaderForwarderInterceptor authInfoForwarderInterceptor(){
        return new HeaderForwarderInterceptor(userDataHeaderName);
    }

    @Bean(name = "acceptLanguageHeaderForwarderInterceptor")
    public HeaderForwarderInterceptor acceptLanguageHeaderForwarderInterceptor(){
        return new HeaderForwarderInterceptor("Accept-Language");
    }

}