package io.foodful.dinner.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "io.foodful")
@ComponentScan({"io.foodful"})
public class DinnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinnerServiceApplication.class, args);
	}
}
