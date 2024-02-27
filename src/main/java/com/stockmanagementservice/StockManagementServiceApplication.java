package com.stockmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StockManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagementServiceApplication.class, args);
	}

}
