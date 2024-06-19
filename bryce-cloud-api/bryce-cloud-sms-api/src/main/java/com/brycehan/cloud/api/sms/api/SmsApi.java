package com.brycehan.cloud.api.sms.api;

import com.brycehan.cloud.api.sms.fallback.SmsApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.enums.SmsType;
import com.brycehan.cloud.common.core.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

/**
 * 短信Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SMS, path = SmsApi.PATH, contextId = "sms", fallbackFactory = SmsApiFallbackImpl.class)
public interface SmsApi {

    String PATH = "/api/sms";

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @param params 参数
     * @return 是否发送成功
     */
    @PostMapping(path = "/send")
    ResponseResult<Boolean> send(@RequestParam String phone, @RequestParam SmsType smsType, @RequestParam LinkedHashMap<String, String> params);

}
