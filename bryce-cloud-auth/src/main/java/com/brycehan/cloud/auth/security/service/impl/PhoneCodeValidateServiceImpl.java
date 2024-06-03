package com.brycehan.cloud.auth.security.service.impl;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.sms.enums.SmsType;
import com.brycehan.cloud.auth.security.service.PhoneCodeValidateService;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
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

    private final SmsApi smsApi;

    @Override
    public boolean validate(String phone, String code) {
        ResponseResult<Boolean> responseResult = this.smsApi.validate(phone, SmsType.LOGIN, code);
        if (responseResult.getCode() == 200) {
            return responseResult.getData();
        }
        return false;
    }

}
