package com.brycehan.cloud.api.system.fallback;

import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.entity.dto.SysParamDto;
import com.brycehan.cloud.api.system.entity.vo.SysParamApiVo;
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
public class SysParamApiFallbackImpl implements FallbackFactory<SysParamApi> {

    @Override
    public SysParamApi create(Throwable cause) {
        log.error("系统服务调用失败，{}", cause.getMessage());

        return new SysParamApi() {
            @Override
            public ResponseResult<Void> save(SysParamDto sysParamDto) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<Void> update(SysParamDto sysParamDto) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<Boolean> exists(String paramKey) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<SysParamApiVo> getByParamKey(String paramKey) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<String> getString(String paramKey) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<Boolean> getBoolean(String paramKey) {
                return ResponseResult.fallback("系统服务调用失败");
            }
        };
    }
}
