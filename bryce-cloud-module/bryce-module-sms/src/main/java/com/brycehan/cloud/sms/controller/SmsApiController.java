package com.brycehan.cloud.sms.controller;

import com.brycehan.cloud.api.module.sms.SmsApi;
import com.brycehan.cloud.common.constant.CacheConstants;
import com.brycehan.cloud.sms.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 短信 Api 实现
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "短信 Api 实现", description = "sms")
@RestController
@RequiredArgsConstructor
public class SmsApiController implements SmsApi {

    private final SmsService smsService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
        return this.smsService.send(phone, templateId, params);
    }

    @Override
    public Boolean validate(String phone, String templateId, String code) {
        // 如果关闭了短信功能，则直接校验不通过
        if (!this.smsService.isSmsEnabled()) {
            return false;
        }

        // 获取缓存验证码
        var codeKey = CacheConstants.SMS_CODE_KEY.concat(templateId).concat(":").concat(phone);
        var codeValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(codeKey);

        if(codeValue != null) {
            // 校验
            return code.equalsIgnoreCase(codeValue);
        }

        return false;
    }

    @Override
    public boolean isSmsEnabled() {
        return this.smsService.isSmsEnabled();
    }

}
