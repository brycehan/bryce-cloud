package com.brycehan.cloud.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 获取请求体数据网关过滤器工厂，解决流不能重复读取问题
 *
 * @author Bryce Han
 * @since 2025/2/12
 */
@Component
public class CacheRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<CacheRequestGatewayFilterFactory.Config> {

    public CacheRequestGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public String name() {
        return "CacheRequestGatewayFilterFactory";
    }

    @Override
    public GatewayFilter apply(Config config) {
        CacheRequestGatewayFilter cacheRequestGatewayFilter = new CacheRequestGatewayFilter();
        Integer order = config.getOrder();
        if (order == null) {
            return cacheRequestGatewayFilter;
        }

        return new OrderedGatewayFilter(cacheRequestGatewayFilter, order);
    }

    public static class CacheRequestGatewayFilter implements GatewayFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            // GET DELETE 不过滤
            HttpMethod method = exchange.getRequest().getMethod();
            if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                return chain.filter(exchange);
            }

            return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, serverHttpRequest -> {
                if (serverHttpRequest == exchange.getRequest()) {
                    return chain.filter(exchange);
                }

                return chain.filter(exchange.mutate().request(serverHttpRequest).build());
            });
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }

    @Data
    public static class Config {
        private Integer order;
    }
}
