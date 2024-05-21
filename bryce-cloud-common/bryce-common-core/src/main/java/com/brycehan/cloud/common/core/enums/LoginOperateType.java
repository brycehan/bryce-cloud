package com.brycehan.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录操作类型
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
@Getter
@AllArgsConstructor
public enum LoginOperateType {
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(0),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(1),
    /**
     * 验证码错误
     */
    CAPTCHA_FAIL(2),
    /**
     * 账号不存在/密码错误
     */
    ACCOUNT_FAIL(3);

    private final int value;
}
