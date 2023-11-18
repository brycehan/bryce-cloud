package com.brycehan.cloud.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Api 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BryceApiAutoConfiguration.class)
public class BryceApiAutoConfiguration {

    public BryceApiAutoConfiguration() {
        log.info("Bryce Api 组件自动配置完成");
    }
}
