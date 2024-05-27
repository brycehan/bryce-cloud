package com.brycehan.cloud.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
     * 忽略的资源地址
     */
    private String[] ignoreUrls = {};

    /**
     * Jwt 配置属性
     */
    private Jwt jwt;

    /**
     * Xss 配置属性
     */
    private Xss xss;


    @Data
    public static class Jwt {

        /**
         * 密钥
         */
        private String secret;

        /**
         * 授权key
         */
        private String authoritiesKey = "auth";

        /**
         * token有效期
         */
        private long tokenValidityInSeconds = 2 * 3600L;

    }

    @Data
    public static class Xss {

        /**
         * 是否开启 XSS
         */
        private boolean enabled;

        /**
         * 忽略的URL列表
         */
        private String[] ignoreUrls = {};

    }

}
