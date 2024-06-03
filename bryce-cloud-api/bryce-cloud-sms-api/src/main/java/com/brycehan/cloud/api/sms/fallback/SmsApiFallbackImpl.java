package com.brycehan.cloud.api.sms.fallback;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.sms.enums.SmsType;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 短信服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class SmsApiFallbackImpl implements FallbackFactory<SmsApi> {

    @Override
    public SmsApi create(Throwable cause) {

        return new SmsApi() {

            @Override
            public ResponseResult<Boolean> send(String phone, SmsType smsType, LinkedHashMap<String, String> params) {
                return ResponseResult.fallback("短信服务调用失败，" + cause.getMessage());
            }

            @Override
            public ResponseResult<Boolean> validate(String phone, SmsType smsType, String code) {
                return ResponseResult.fallback("短信服务调用失败，" + cause.getMessage());
            }

            @Override
            public ResponseResult<Boolean> isSmsEnabled() {
                return ResponseResult.fallback("短信服务调用失败，" + cause.getMessage());
            }
        };
    }
}
