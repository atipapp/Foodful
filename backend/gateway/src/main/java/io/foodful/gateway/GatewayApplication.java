package io.foodful.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableFeignClients(basePackages = "io.foodful")
@ComponentScan(basePackages = {"io.foodful"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "io.foodful.commons.*")
        }
)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
