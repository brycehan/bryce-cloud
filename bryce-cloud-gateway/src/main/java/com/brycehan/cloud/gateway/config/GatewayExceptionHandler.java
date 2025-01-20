package com.brycehan.cloud.gateway.config;

import com.brycehan.cloud.common.core.base.response.HttpResponseStatus;
import com.brycehan.cloud.gateway.utils.ReactiveUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关异常处理
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
@Slf4j
@Order(-1)
@Configuration
@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final HttpClientProperties httpClientProperties;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("网关异常，请求路径：{}，异常信息：{}", exchange.getRequest().getPath(), ex.getMessage());

        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), ex.getMessage());
        }

        // 404
        if (ex instanceof NoResourceFoundException || ex instanceof NotFoundException) {
            return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_NOT_FOUND);
        }

        // 504
        if (ex instanceof ResponseStatusException && ((ResponseStatusException) ex).getStatusCode().value() == 504) {
            return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_GATEWAY_TIMEOUT, httpClientProperties.getResponseTimeout().getSeconds());
        }

        // 其它异常
        return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }

}
