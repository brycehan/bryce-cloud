package com.brycehan.cloud.common.api.base;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;

/**
 * Feign枚举转换器
 *
 * @author Bryce Han
 * @since 2025/1/8
 */
@Component
public class EnumFeignFormatterRegistrar implements FeignFormatterRegistrar {

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addConverter(new EnumToStringConverter());
    }
}
