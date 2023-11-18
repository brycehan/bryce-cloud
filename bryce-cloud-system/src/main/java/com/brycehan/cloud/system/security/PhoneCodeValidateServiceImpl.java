package com.brycehan.cloud.system.security;

import com.brycehan.cloud.api.module.sms.SmsApi;
import com.brycehan.cloud.framework.security.phone.PhoneCodeValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 手机验证码校验服务实现
 *
 * @author Bryce Han
 * @since 2023/10/9
 */
@Service
@RequiredArgsConstructor
public class PhoneCodeValidateServiceImpl implements PhoneCodeValidateService {

    private final SmsApi smsApi;

    @Value("${sms.login-template-id}")
    private String loginTemplateId;

    @Override
    public boolean validate(String phone, String code) {
        return this.smsApi.validate(phone, loginTemplateId, code);
    }

}
