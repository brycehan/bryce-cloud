package com.brycehan.cloud.gateway.config;

import com.brycehan.cloud.common.core.response.HttpResponseStatus;
import com.brycehan.cloud.gateway.utils.ReactiveUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.resource.NoResourceFoundException;
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
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("网关异常，请求路径：{}，异常信息：{}", exchange.getRequest().getPath(), ex.getMessage());

        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_INTERNAL_ERROR, ex.getMessage());
        }

        if (ex instanceof NoResourceFoundException || ex instanceof NotFoundException) {
            return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_NOT_FOUND);
        }

        return ReactiveUtils.exceptionResponse(exchange, HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }

}
