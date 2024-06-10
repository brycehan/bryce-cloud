package com.brycehan.cloud.gateway.utils;


import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 令牌工具类
 *
 * @since 2023/8/30
 * @author Bryce Han
 */
@Slf4j
public class TokenUtils {

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

}
