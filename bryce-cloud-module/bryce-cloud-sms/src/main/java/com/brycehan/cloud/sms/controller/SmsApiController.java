package com.brycehan.cloud.sms.controller;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.sms.enums.SmsType;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.sms.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
@Tag(name = "短信Api实现")
@RestController
@RequiredArgsConstructor
public class SmsApiController implements SmsApi {

    private final SmsService smsService;
    private final StringRedisTemplate stringRedisTemplate;
    private final Environment environment;

    @Override
    public ResponseResult<Boolean> send(String phone, SmsType smsType, LinkedHashMap<String, String> params) {
        String templateIdKey = "sms:" + smsType.value() + "-template-id";
        String templateId = this.environment.getProperty(templateIdKey);

        boolean send = this.smsService.send(phone, templateId, params);
        return ResponseResult.ok(send);
    }

    @Override
    public ResponseResult<Boolean> validate(String phone, SmsType smsType, String code) {
        // 如果关闭了短信功能，则直接校验不通过
        if (!this.smsService.isSmsEnabled()) {
            return ResponseResult.ok(false);
        }
        String templateIdKey = "sms:" + smsType.value() + "-template-id";
        String templateId = this.environment.getProperty(templateIdKey);

        // 获取缓存验证码
        assert templateId != null;
        var codeKey = CacheConstants.SMS_CODE_KEY.concat(templateId).concat(":").concat(phone);
        var codeValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(codeKey);

        if(codeValue != null) {
            // 校验
            return ResponseResult.ok(code.equalsIgnoreCase(codeValue));
        }

        return ResponseResult.ok(false);
    }

    @Override
    public ResponseResult<Boolean> isSmsEnabled() {
        Boolean smsEnabled = this.smsService.isSmsEnabled();
        return ResponseResult.ok(smsEnabled);
    }

}
