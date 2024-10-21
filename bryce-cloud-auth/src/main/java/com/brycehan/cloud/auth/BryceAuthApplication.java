package com.brycehan.cloud.auth;

import com.brycehan.cloud.common.server.common.EnableBryceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bryce认证应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableBryceConfig
@SpringBootApplication
public class BryceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceAuthApplication.class, args);
    }

}

