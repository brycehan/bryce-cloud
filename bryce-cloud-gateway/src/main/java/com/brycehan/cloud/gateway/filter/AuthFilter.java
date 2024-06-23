package com.brycehan.cloud.gateway.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.gateway.config.properties.AuthProperties;
import com.brycehan.cloud.gateway.utils.AuthPathParser;
import com.brycehan.cloud.gateway.utils.JwtTokenParser;
import com.brycehan.cloud.gateway.utils.ReactiveUtils;
import com.brycehan.cloud.gateway.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
            return ReactiveUtils.unauthorizedResponse(exchange, "访问令牌不能为空");
        }

        // 删除非法请求头
        mutate.headers(httpHeaders -> httpHeaders.remove(JwtConstants.USER_KEY)).build();
        mutate.headers(httpHeaders -> httpHeaders.remove(JwtConstants.USER_DATA)).build();

        // 校验并解析令牌
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtTokenParser.validateToken(accessToken);
        } catch (TokenExpiredException e) {
            return ReactiveUtils.unauthorizedResponse(exchange, "登录状态已过期");
        } catch (Exception e) {
            return ReactiveUtils.unauthorizedResponse(exchange, "访问令牌校验失败");
        }

        Map<String, Claim> claimMap = decodedJWT.getClaims();
        // 设置用户信息到请求头
        String userKey = JwtTokenParser.getUserKey(claimMap);
        String userData = JwtTokenParser.getUserData(claimMap);
        ReactiveUtils.addHeader(mutate, JwtConstants.USER_KEY, userKey);
        ReactiveUtils.addHeader(mutate, JwtConstants.USER_DATA, userData);

        // 删除处理过的请求头
        mutate.headers(httpHeaders -> httpHeaders.remove(HttpHeaders.AUTHORIZATION)).build();
        // 内部请求来源参数清除
        mutate.headers(httpHeaders -> httpHeaders.remove(DataConstants.INNER_CALL)).build();

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

}
