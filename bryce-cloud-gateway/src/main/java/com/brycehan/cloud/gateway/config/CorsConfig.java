package com.brycehan.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 跨域配置
 *
 * @author Bryce Han
 * @since 2025/2/11
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域过滤器
     *
     * @return CorsWebFilter
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        final CorsConfiguration config = new CorsConfiguration();
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 设置允许凭证共享
        config.setAllowCredentials(true);
        // 预检请求的缓存时间7200秒
        config.setMaxAge(7200L);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 添加映射路径，拦截一切请求
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
