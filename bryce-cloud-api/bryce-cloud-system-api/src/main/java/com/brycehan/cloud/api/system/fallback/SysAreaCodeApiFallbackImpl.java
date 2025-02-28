package com.brycehan.cloud.api.system.fallback;

import com.brycehan.cloud.api.system.client.SysAreaCodeClient;
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
public class SysAreaCodeApiFallbackImpl implements FallbackFactory<SysAreaCodeClient> {

    @Override
    public SysAreaCodeClient create(Throwable cause) {
        log.error("系统服务调用失败", cause);

        return new SysAreaCodeClient() {
            @Override
            public ResponseResult<String> getExtNameByCode(String areaCode) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<String> getFullLocation(String areaCode) {
                return ResponseResult.fallback("系统服务调用失败");
            }
        };

    }
}
