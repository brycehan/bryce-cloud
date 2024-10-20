package com.brycehan.cloud.api.sms.fallback;

import com.brycehan.cloud.api.sms.client.SmsClient;
import com.brycehan.cloud.common.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 短信服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class SmsApiFallbackImpl implements FallbackFactory<SmsClient> {

    @Override
    public SmsClient create(Throwable cause) {
        log.error("短信服务调用失败，{}", cause.getMessage());
        return (phone, smsType, params) -> ResponseResult.fallback("短信服务调用失败");
    }
}

