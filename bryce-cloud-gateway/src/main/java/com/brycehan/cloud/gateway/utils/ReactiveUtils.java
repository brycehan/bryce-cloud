package com.brycehan.cloud.gateway.utils;

import com.brycehan.cloud.common.core.response.HttpResponseStatus;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Reactive 工具类
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
@Slf4j
public class ReactiveUtils {

    /**
     * 添加请求头
     *
     * @param mutate mutate
     * @param name 名称
     * @param value 值
     */
    public static void addHeader(ServerHttpRequest.Builder mutate, String name, String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }

        mutate.header(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
    }

    /**
     * 未授权响应处理
     *
     * @param exchange exchange
     * @param message 消息
     * @return 响应结果
     */
    public static Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        log.error("unauthorizedResponse，鉴权异常处理，请求路径，{}", exchange.getRequest().getPath());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseResult<?> responseResult = ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED.code(), message);

        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtils.writeValueAsString(responseResult).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 异常响应处理
     *
     * @param exchange exchange
     * @param message 消息
     * @return 响应结果
     */
    public static Mono<Void> exceptionResponse(ServerWebExchange exchange, HttpResponseStatus httpResponseStatus, String message) {
        log.error("unauthorizedResponse，其它异常处理，请求路径，{}", exchange.getRequest().getPath());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseResult<?> responseResult = ResponseResult.error(httpResponseStatus.code(), message);

        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtils.writeValueAsString(responseResult).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 异常响应处理
     *
     * @param exchange exchange
     * @return 响应结果
     */
    public static Mono<Void> exceptionResponse(ServerWebExchange exchange, HttpResponseStatus httpResponseStatus) {
        return exceptionResponse(exchange, httpResponseStatus, httpResponseStatus.message());
    }

}
