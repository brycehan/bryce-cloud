package com.brycehan.cloud.system.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 登录操作类型
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
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
     * 账号密码错误
     */
    ACCOUNT_FAIL(3);

    @EnumValue
    @JsonValue
    private final int value;
}
