package com.brycehan.cloud.gateway.filter;

import com.auth0.jwt.interfaces.Claim;
import com.brycehan.cloud.common.core.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.gateway.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 网关鉴权
 *
 * @author Bryce Han
 * @since 2024/5/24
 */
@Slf4j
@Order(-100)
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class AuthFilter implements GlobalFilter {

    private final JwtProperties jwtProperties;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        log.info("请求地址：{}", url);

        String accessToken = TokenUtils.getAccessToken(request);

        if (StringUtils.isNotEmpty(accessToken)) {
            // 校验令牌
            boolean validated = jwtTokenProvider.validateToken(accessToken);
            if (!validated) {
                return unauthorizedResponse(exchange, "令牌校验失败");
            }

            // 解析令牌
            Map<String, Claim> claimMap = jwtTokenProvider.parseToken(accessToken);
            if (claimMap == null) {
                log.info("claimMap is null");
            }

            // 设置用户信息到请求头
            if (claimMap != null) {
                String userKey = JwtTokenProvider.getUserKey(claimMap);
                String userData = JwtTokenProvider.getUserData(claimMap);
                addHeader(mutate, JwtConstants.USER_KEY, userKey);
                addHeader(mutate, JwtConstants.USER_DATA, userData);

            }
        }

        // 内部请求来源参数清除
        mutate.headers(httpHeaders -> httpHeaders.remove(JwtConstants.INNER_CALL_HEADER)).build();

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    /**
     * 添加请求头
     *
     * @param mutate mutate
     * @param name 名称
     * @param value 值
     */
    private void addHeader(ServerHttpRequest.Builder mutate, String name, String value) {
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
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        log.error("鉴权异常处理，请求路径，{}", exchange.getRequest().getPath());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseResult<?> responseResult = ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED.code(), message);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtils.writeValueAsString(responseResult).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

}
