package com.brycehan.cloud.storage;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Bryce存储应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableMethodCache(basePackages = "com.brycehan.cloud.storage")
@EnableDiscoveryClient
@SpringBootApplication
public class BryceStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceStorageApplication.class, args);
    }

}
