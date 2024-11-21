package com.brycehan.cloud.api.system.fallback;

import com.brycehan.cloud.api.system.client.MaUserClient;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
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
public class MaUserApiFallbackImpl implements FallbackFactory<MaUserClient> {

    @Override
    public MaUserClient create(Throwable cause) {
        log.error("系统服务调用失败，{}", cause.getMessage());
        return openid -> ResponseResult.fallback("系统服务调用失败");
    }
}
