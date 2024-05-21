package com.brycehan.cloud.system.security.config;

import com.brycehan.cloud.api.sms.SmsApi;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * 登录配置
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Configuration
public class LoginConfig {

    @Bean
    @ConditionalOnMissingBean
    SmsApi smsApi() {
        return new SmsApi() {
            @Override
            public ResponseResult<Boolean> send(String phone, String templateId, LinkedHashMap<String, String> params) {
                return ResponseResult.error("短信服务没有开启");
            }

            @Override
            public ResponseResult<Boolean> validate(String phone, String templateId, String code) {
                return ResponseResult.ok(false);
            }

            @Override
            public ResponseResult<Boolean> isSmsEnabled() {
                return ResponseResult.ok(false);
            }
        };
    }
}
