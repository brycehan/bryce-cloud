package com.brycehan.cloud.api.sms.api;

import com.brycehan.cloud.api.sms.enums.SmsType;
import com.brycehan.cloud.api.sms.fallback.SmsApiFallbackImpl;
import com.brycehan.cloud.common.core.ServerNames;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

/**
 * 短信服务
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SMS, contextId = "sms", fallbackFactory = SmsApiFallbackImpl.class)
public interface SmsApi {

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @param params 参数
     * @return 是否发送成功
     */
    @PostMapping(path = "/sms/api/send")
    ResponseResult<Boolean> send(@RequestParam String phone, @RequestParam SmsType smsType, @RequestParam LinkedHashMap<String, String> params);

    /**
     * 校验短信验证码
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @param code 验证码
     * @return 是否校验成功
     */
    @PostMapping(path = "/sms/api/validate")
    ResponseResult<Boolean> validate(@RequestParam String phone, @RequestParam SmsType smsType, @RequestParam String code);

    /**
     * 是否开启短信功能
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    @GetMapping(path = "/sms/api/isSmsEnabled")
    ResponseResult<Boolean> isSmsEnabled();

}
