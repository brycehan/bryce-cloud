package com.brycehan.cloud.common.api.util;

import feign.Request;

import java.util.concurrent.TimeUnit;

/**
 * Feign工具类
 *
 * @author Bryce Han
 * @since 2025/1/20
 */
@SuppressWarnings("unused")
public class FeignUtils {

    /**
     * 请求超时时间 60秒
     */
    public static final Request.Options OPTIONS_60S = new Request.Options(60, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
}
