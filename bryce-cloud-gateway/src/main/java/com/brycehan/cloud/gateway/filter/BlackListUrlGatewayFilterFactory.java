package com.brycehan.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.gateway.utils.ReactiveUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 黑名单 URL 网关过滤器工厂
 *
 * @author Bryce Han
 * @since 2025/2/12
 */
@Slf4j
@Component
public class BlackListUrlGatewayFilterFactory extends AbstractGatewayFilterFactory<BlackListUrlGatewayFilterFactory.Config> {

    public BlackListUrlGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            if (config.matchBlackListUrl(url)) {
                log.info("匹配到黑名单 URL，请求被拦截：{}", url);
                return ReactiveUtils.exceptionResponse(exchange, 403, "请求地址不允许访问");
            }

            return chain.filter(exchange);
        });
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("blackListUrls");
    }

    public static class Config {
        private List<String> blackListUrls;
        private final List<Pattern> blackListUrlsPatterns = new ArrayList<>();

        public boolean matchBlackListUrl(String url) {
            return !blackListUrlsPatterns.isEmpty() && blackListUrlsPatterns.stream().anyMatch(p -> p.matcher(url).matches());
        }

        @SuppressWarnings("unused")
        public void setBlackListUrls(String blackListUrls) {
            if (StrUtil.isNotBlank(blackListUrls)) {
                this.blackListUrls = Arrays.stream(blackListUrls.trim().split(" ")).map(String::trim).toList();
            }
            this.blackListUrlsPatterns.clear();
            if (this.blackListUrls != null) {
                this.blackListUrls.forEach(url -> blackListUrlsPatterns.add(Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE)));
            }
        }

        @SuppressWarnings("unused")
        public List<String> getBlackListUrls() {
            return blackListUrls;
        }
    }
}
