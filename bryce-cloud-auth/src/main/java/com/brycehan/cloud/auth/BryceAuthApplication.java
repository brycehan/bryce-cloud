package com.brycehan.cloud.auth;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Bryce认证应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableMethodCache(basePackages = "com.brycehan.cloud.auth")
@EnableDiscoveryClient
@SpringBootApplication
public class BryceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceAuthApplication.class, args);
    }

}
