package com.brycehan.cloud.api.fallback;

import com.brycehan.cloud.api.sms.SmsApi;
import com.brycehan.cloud.common.base.http.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 短信服务降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class SmsFallbackFactory implements FallbackFactory<SmsApi> {
    @Override
    public SmsApi create(Throwable cause) {
        log.error("短信服务调用失败：{}", cause.getMessage());
        return new SmsApi() {
            @Override
            public ResponseResult<Boolean> send(String phone, String templateId, LinkedHashMap<String, String> params) {
                return ResponseResult.error("短信服务调用失败：" + cause.getMessage());
            }

            @Override
            public ResponseResult<Boolean> validate(String phone, String templateId, String code) {
                return ResponseResult.error("短信服务调用失败：" + cause.getMessage());
            }

            @Override
            public ResponseResult<Boolean> isSmsEnabled() {
                return ResponseResult.error("短信服务调用失败：" + cause.getMessage());
            }
        };
    }
}
