package com.brycehan.cloud.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * 登录操作类型
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
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
     * 账号不存在/密码错误
     */
    ACCOUNT_FAIL(3);

    private final int value;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    public Integer value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LoginOperateType getByValue(Integer value) {
        for (LoginOperateType loginOperateType : values()) {
            if (loginOperateType.value == value) {
                return loginOperateType;
            }
        }
        return null;
    }

}
