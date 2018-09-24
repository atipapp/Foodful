package io.foodful.feign.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class HeaderForwarderInterceptor implements RequestInterceptor {

    private String headerName;

    public HeaderForwarderInterceptor(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String header = request.getHeader(headerName);
        if(header != null && !header.isEmpty()){
            requestTemplate.header(headerName, header);
        }
    }

}