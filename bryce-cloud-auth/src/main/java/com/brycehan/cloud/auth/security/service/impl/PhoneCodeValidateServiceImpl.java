package com.brycehan.cloud.auth.security.service.impl;

import com.brycehan.cloud.auth.security.service.PhoneCodeValidateService;
import com.brycehan.cloud.auth.service.AuthSmsService;
import com.brycehan.cloud.common.core.enums.SmsType;
import lombok.RequiredArgsConstructor;
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

    private final AuthSmsService authSmsService;

    @Override
    public boolean validate(String phone, String code) {
        return authSmsService.validate(phone, code, SmsType.LOGIN);
    }

}
