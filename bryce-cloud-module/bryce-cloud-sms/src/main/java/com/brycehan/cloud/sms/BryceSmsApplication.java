package com.brycehan.cloud.sms;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Bryce System 应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableMethodCache(basePackages = "com.brycehan.cloud.sms")
@EnableDiscoveryClient
@SpringBootApplication
public class BryceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSmsApplication.class, args);
    }

}
