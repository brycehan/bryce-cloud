package com.brycehan.cloud.auth;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Bryce认证应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableMethodCache(basePackages = "com.brycehan.cloud.auth")
public class BryceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceAuthApplication.class, args);
    }

}

