package com.brycehan.cloud.common.security.common;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.brycehan.cloud.common.core.constant.TokenConstants.SOURCE_CLIENT_HEADER;

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
     * 获取请求来源客户端，不存在时默认返回未知UNKNOWN
     *
     * @param request 请求request
     * @return 来源客户端
     */
    public static SourceClientType getSourceClient(HttpServletRequest request) {
        String sourceClient = request.getHeader(SOURCE_CLIENT_HEADER);

         if (StrUtil.isBlank(sourceClient)) {
            return SourceClientType.UNKNOWN;
        }

        SourceClientType sourceClientType = SourceClientType.of(sourceClient);
        return Objects.requireNonNullElse(sourceClientType, SourceClientType.UNKNOWN);
    }
}
