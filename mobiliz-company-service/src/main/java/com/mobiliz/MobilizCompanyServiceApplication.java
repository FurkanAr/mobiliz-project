package com.mobiliz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
public class MobilizCompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilizCompanyServiceApplication.class, args);
	}

}
