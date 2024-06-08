package com.brycehan.cloud.api.system.fallback;

import com.brycehan.cloud.api.system.api.SysLoginLogApi;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class SysLoginLogApiFallbackImpl implements FallbackFactory<SysLoginLogApi> {

    @Override
    public SysLoginLogApi create(Throwable cause) {
        log.error("系统服务调用失败，{}", cause.getMessage());
        return sysLoginLogDto -> ResponseResult.fallback("系统服务调用失败");
    }
}
