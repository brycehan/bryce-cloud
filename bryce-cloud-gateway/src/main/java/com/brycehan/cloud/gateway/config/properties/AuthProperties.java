package com.brycehan.cloud.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 认证属性
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bryce.auth")
public class AuthProperties {

    /**
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls;


    @Data
    public static class IgnoreUrls {

        /**
         * GET 类型忽略的资源地址
         */
        private String[] get = { "/webjars/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/controller-docs/**"};

        /**
         * POST 类型忽略的资源地址
         */
        private String[] post = {};

        /**
         * PUT 类型忽略的资源地址
         */
        private String[] put = {};

        /**
         * DELETE 类型忽略的资源地址
         */
        private String[] delete = {};

        /**
         * 所有类型忽略的资源地址
         */
        private String[] all = {};
    }
}
