package com.brycehan.cloud.gateway.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.cloud.common.core.response.HttpResponseStatus;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.gateway.config.properties.AuthProperties;
import com.brycehan.cloud.gateway.utils.AuthPathParser;
import com.brycehan.cloud.gateway.utils.JwtTokenParser;
import com.brycehan.cloud.gateway.utils.TokenUtils;
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
 * 鉴权过滤
 *
 * @author Bryce Han
 * @since 2024/5/24
 */
@Slf4j
@Order(-100)
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthFilter implements GlobalFilter {

    private final JwtTokenParser jwtTokenParser;
    private final AuthPathParser authPathParser;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        log.info("请求地址：{}", url);

        // 跳过不需要校验的请求
        if (authPathParser.authIgnoreMatch(url)) {
            return chain.filter(exchange);
        }

        String accessToken = TokenUtils.getAccessToken(request);
        if (StringUtils.isEmpty(accessToken)) {
            return unauthorizedResponse(exchange, "访问令牌不能为空");
        }

        // 删除非法请求头
        mutate.headers(httpHeaders -> httpHeaders.remove(JwtConstants.USER_KEY)).build();
        mutate.headers(httpHeaders -> httpHeaders.remove(JwtConstants.USER_DATA)).build();

        // 校验并解析令牌
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtTokenParser.validateToken(accessToken);
        } catch (TokenExpiredException e) {
            return unauthorizedResponse(exchange, "登录状态已过期");
        } catch (Exception e) {
            return unauthorizedResponse(exchange, "访问令牌校验失败");
        }

        Map<String, Claim> claimMap = decodedJWT.getClaims();
        // 设置用户信息到请求头
        String userKey = JwtTokenParser.getUserKey(claimMap);
        String userData = JwtTokenParser.getUserData(claimMap);
        addHeader(mutate, JwtConstants.USER_KEY, userKey);
        addHeader(mutate, JwtConstants.USER_DATA, userData);

        // 删除处理过的请求头
        mutate.headers(httpHeaders -> httpHeaders.remove(HttpHeaders.AUTHORIZATION)).build();
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
    public static Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        log.error("鉴权异常处理，请求路径，{}", exchange.getRequest().getPath());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseResult<?> responseResult = ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED.code(), message);

        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtils.writeValueAsString(responseResult).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

}
