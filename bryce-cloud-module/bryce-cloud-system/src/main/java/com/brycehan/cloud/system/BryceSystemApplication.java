package com.brycehan.cloud.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;


/**
 * Bryce系统应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableRetry
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class BryceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceSystemApplication.class, args);
    }

}
