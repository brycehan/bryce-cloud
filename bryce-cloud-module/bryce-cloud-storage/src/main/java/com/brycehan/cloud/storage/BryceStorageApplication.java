package com.brycehan.cloud.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


/**
 * Bryce存储应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class BryceStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceStorageApplication.class, args);
    }

}
