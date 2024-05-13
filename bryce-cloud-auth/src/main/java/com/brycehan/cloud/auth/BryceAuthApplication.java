package com.brycehan.cloud.auth;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;


/**
 * Bryce认证应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableFeignClients(basePackages = "com.brycehan.cloud")
@EnableMethodCache(basePackages = "com.brycehan.cloud")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.brycehan.cloud")
public class BryceAuthApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BryceAuthApplication.class, args);
        String[] beans = run.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            System.out.println(bean + " of Type :: " + run.getBean(bean).getClass());
        }
    }

}
