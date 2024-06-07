package com.brycehan.cloud.sms.controller;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.enums.SmsType;
import com.brycehan.cloud.sms.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 短信 Api
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "短信Api")
@RestController
@RequiredArgsConstructor
public class SmsApiController implements SmsApi {

    private final SmsService smsService;
    private final Environment environment;

    @Override
    public ResponseResult<Boolean> send(String phone, SmsType smsType, LinkedHashMap<String, String> params) {
        String templateIdKey = "sms:" + smsType.value() + "-template-id";
        String templateId = this.environment.getProperty(templateIdKey);

        boolean send = this.smsService.send(phone, templateId, params);
        return ResponseResult.ok(send);
    }

}
