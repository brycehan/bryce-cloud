package com.brycehan.cloud.system;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;


/**
 * Bryce System 应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableRetry
@EnableMethodCache(basePackages = "com.brycehan.cloud.system")
@EnableDiscoveryClient
@SpringBootApplication
public class BryceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSystemApplication.class, args);
    }

}
