package com.brycehan.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关应用
 *
 * @since 2023/11/15
 * @author Bryce Han
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BryceGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BryceGatewayApplication.class, args);
	}

}
