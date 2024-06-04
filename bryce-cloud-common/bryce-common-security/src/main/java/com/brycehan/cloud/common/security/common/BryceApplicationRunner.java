package com.brycehan.cloud.common.security.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 配置子线程共享登录用户信息
 *
 * @author Bryce Han
 * @since 2024/6/4
 */
@Slf4j
@Component
public class BryceApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
