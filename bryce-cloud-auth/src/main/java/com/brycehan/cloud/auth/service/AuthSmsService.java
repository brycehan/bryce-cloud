package com.brycehan.cloud.auth.service;

import com.brycehan.cloud.auth.entity.vo.SmsCodeVo;
import com.brycehan.cloud.common.core.enums.SmsType;

/**
 * 认证短信服务
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
public interface AuthSmsService {

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @return 发送结果（true：发送成功，false：发送失败）
     */
    SmsCodeVo sendCode(String phone, SmsType smsType);

    /**
     * 校验验证码
     *
     * @param key  key
     * @param code 验证码
     * @param smsType 短信类型
     * @return 校验结果（true：正确，false：错误）
     */
    boolean validate(String key, String code, SmsType smsType);

    /**
     * 获取短信验证码开关
     *
     * @param smsType 验证码类型
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean smsEnabled(SmsType smsType);

    /**
     * 获取短信开关
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean smsEnabled();

}
