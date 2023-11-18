package com.brycehan.cloud.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bryce Wechat 应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@MapperScan(basePackages = "com.brycehan.cloud.*.mapper")
@SpringBootApplication
public class BryceWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceWechatApplication.class, args);
    }

}
