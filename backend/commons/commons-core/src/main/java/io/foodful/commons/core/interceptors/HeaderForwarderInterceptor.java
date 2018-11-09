package io.foodful.commons.core.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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
            log.info("Forwarding header {} with value {}", headerName, header);
            requestTemplate.header(headerName, header);
        }
    }

}