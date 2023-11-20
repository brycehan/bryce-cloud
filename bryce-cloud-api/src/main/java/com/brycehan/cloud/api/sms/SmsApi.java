package com.brycehan.cloud.api.sms;

import com.brycehan.cloud.api.ServerNames;
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
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sms")
public interface SmsApi {

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param templateId 模板ID
     * @param params 参数
     * @return 是否发送成功
     */
    @PostMapping(path = "/api/sms/send")
    Boolean send(@RequestParam String phone, @RequestParam String templateId, @RequestParam LinkedHashMap<String, String> params);

    /**
     * 校验短信验证码
     *
     * @param phone 手机号
     * @param templateId 模板ID
     * @param code 验证码
     * @return 是否校验成功
     */
    @PostMapping(path = "/api/sms/validate")
    Boolean validate(@RequestParam String phone, @RequestParam String templateId, @RequestParam String code);

    /**
     * 是否开启短信功能
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    @GetMapping(path = "/api/sms/isSmsEnabled")
    Boolean isSmsEnabled();

}
