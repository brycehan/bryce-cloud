package com.brycehan.cloud.system;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * Bryce System 应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableFeignClients(basePackages = "com.brycehan.cloud")
@EnableMethodCache(basePackages = "com.brycehan.cloud")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.brycehan.cloud")
public class BryceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSystemApplication.class, args);
    }

}
