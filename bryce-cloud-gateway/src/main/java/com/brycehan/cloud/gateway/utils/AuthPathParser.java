package com.brycehan.cloud.gateway.utils;

import com.brycehan.cloud.gateway.config.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;

/**
 * 权限路径解析器
 *
 * @author Bryce Han
 * @since 2024/5/27
 */
@Component
@RequiredArgsConstructor
public class AuthPathParser {

    private final AuthProperties authProperties;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 免登录需要忽略的路径匹配
     *
     * @param url 需要匹配的路径
     * @return 是否匹配
     */
    public boolean authIgnoreMatch(String url) {
        return Arrays.stream(authProperties.getIgnoreUrls())
                .anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }

    /**
     * Xss 忽略的路径匹配
     *
     * @param url 需要匹配的路径
     * @return 是否匹配
     */
    public boolean xssIgnoreMatch(String url) {
        return Arrays.stream(authProperties.getXss().getIgnoreUrls())
                .anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }

}
