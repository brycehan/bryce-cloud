package com.brycehan.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Bryce Wechat 应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BryceWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceWechatApplication.class, args);
    }

}
