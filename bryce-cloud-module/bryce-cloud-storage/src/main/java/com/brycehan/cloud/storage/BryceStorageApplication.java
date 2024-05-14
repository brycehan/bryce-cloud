package com.brycehan.cloud.storage;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * Bryce存储应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableFeignClients(basePackages = "com.brycehan.cloud")
@EnableMethodCache(basePackages = "com.brycehan.cloud")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.brycehan.cloud")
public class BryceStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceStorageApplication.class, args);
    }

}
