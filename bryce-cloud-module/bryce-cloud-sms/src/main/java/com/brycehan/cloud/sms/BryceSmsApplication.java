package com.brycehan.cloud.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Bryce短信应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BryceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSmsApplication.class, args);
    }

}
