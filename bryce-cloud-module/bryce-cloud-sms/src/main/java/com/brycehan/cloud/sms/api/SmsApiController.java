package com.brycehan.cloud.sms.api;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.common.core.enums.SmsType;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.sms.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(path = SmsApi.PATH)
@RestController
@RequiredArgsConstructor
public class SmsApiController implements SmsApi {

    private final SmsService smsService;
    private final Environment environment;

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @param params 短信参数
     * @return 是否发送成功
     */
    @Override
    @Operation(summary = "发送短信")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Boolean> send(String phone, SmsType smsType, LinkedHashMap<String, String> params) {
        String templateIdKey = "sms." + smsType.value() + "-template-id";
        String templateId = this.environment.getProperty(templateIdKey);

        boolean send = this.smsService.send(phone, templateId, params);
        return ResponseResult.ok(send);
    }

}
