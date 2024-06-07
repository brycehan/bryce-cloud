package com.brycehan.cloud.common.core.base;

import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.enums.SmsType;

/**
 * Redis Key 管理
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
public class RedisKeys {

    /**
     * 获取验证码key
     *
     * @param key 验证码uuid
     * @return 验证码key
     */
    public static String getCaptchaKey(String key) {
        return CacheConstants.CAPTCHA_CODE_KEY.concat(key);
    }

    /**
     * 获取短信验证码key
     *
     * @param key 验证码uuid
     * @return 验证码key
     */
    public static String getSmsCodeKey(String key, SmsType smsType) {
        return CacheConstants.SMS_CODE_KEY + smsType.value() + ":" + key;
    }

    public static String getOperateLogKey() {
        return "system:log";
    }

}
