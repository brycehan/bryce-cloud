package com.brycehan.cloud.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


/**
 * Bryce短信应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class BryceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSmsApplication.class, args);
    }

}
