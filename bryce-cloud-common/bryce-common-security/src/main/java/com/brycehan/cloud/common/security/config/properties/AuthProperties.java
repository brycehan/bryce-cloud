package com.brycehan.cloud.common.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 认证属性
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.auth")
public class AuthProperties {

    /**
     * Jwt 配置属性
     */
    private Jwt jwt = new Jwt();

    /**
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls = new IgnoreUrls();

    /**
     * 登录用户缓存开关
     */
    private boolean loginUserCacheEnabled = false;

    @Data
    public static class Jwt {

        /**
         * 密钥（生产环境需要配置）
         */
        private String secret = "UZCiSM60eRJMOFA9mbiy";;

        /**
         * 授权key
         */
        private String authoritiesKey = "auth";

        /**
         * 默认token有效期
         */
        private Duration defaultTokenValidity = Duration.ofHours(2L);

        /**
         * App token有效期
         */
        private Duration appTokenValidity =  Duration.ofDays(30L);
    }

    @Data
    public static class IgnoreUrls {

        /**
         * GET 类型忽略的资源地址
         */
        private String[] get = { "/webjars/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**"};

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
