package com.brycehan.cloud.framework.security;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.constant.JwtConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

/**
 * 令牌工具类
 *
 * @since 2023/8/30
 * @author Bryce Han
 */
@Slf4j
public class TokenUtils {

    /**
     * 生成安全的uuid
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID(true).toString();
    }

    /**
     * 获取请求携带的令牌
     *
     * @param request 请求request
     * @return optional令牌
     */
    public static String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return bearerToken.split(" ")[1].trim();
        }

        log.debug("请求头不含 jwt token");

        return StrUtil.EMPTY;
    }
}
