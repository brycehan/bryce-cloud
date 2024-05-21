package com.brycehan.cloud.common.core.base;

import com.brycehan.cloud.common.core.constant.CacheConstants;

/**
 * Redis Key 管理
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
public class RedisKeys {

    /**
     * 获取验证码 key
     *
     * @param key 验证码 uuid
     * @return 验证码 key
     */
    public static String getCaptchaKey(String key) {
        return CacheConstants.CAPTCHA_CODE_KEY.concat(key);
    }

    public static String getOperateLogKey() {
        return "sys:log";
    }

}
