package com.brycehan.cloud.gateway.utils;


import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Arrays;

/**
 * 令牌工具类
 *
 * @since 2023/8/30
 * @author Bryce Han
 */
@Slf4j
public class TokenUtils {

    public static final String SOURCE_CLIENT_HEADER = "source-client";

    /**
     * 获取请求携带的令牌
     *
     * @param request 请求request
     * @return optional令牌
     */
    public static String getAccessToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return bearerToken.split(" ")[1].trim();
        }

        log.debug("请求头不含 jwt token");

        return StrUtil.EMPTY;
    }

    /**
     * 获取请求来源客户端
     *
     * @param request 请求request
     * @return 来源客户端
     */
    public static String getSourceClient(ServerHttpRequest request) {
        String loginClient = request.getHeaders().getFirst(SOURCE_CLIENT_HEADER);

        if (StrUtil.isNotBlank(loginClient) && Arrays.stream(SourceClientType.values()).anyMatch(t -> t.value().equals(loginClient))) {
            return loginClient;
        }

        throw new ServerException("非法来源客户端请求");
    }

}
