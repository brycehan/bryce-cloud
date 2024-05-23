package com.brycehan.cloud.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Sentinel配置
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@Configuration
public class SentinelConfig {

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        GatewayCallbackManager.setBlockHandler((exchange, throwable) -> {
            Map<String, Object> error = new HashMap<>();
            error.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
            error.put("message", "访问量过大，稍后请重试");
            error.put("uri", exchange.getRequest().getURI());
            error.put("time", LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(error), Map.class);
        });
    }
}
