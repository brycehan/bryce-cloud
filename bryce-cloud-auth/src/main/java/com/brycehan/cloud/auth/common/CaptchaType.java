package com.brycehan.cloud.auth.common;

import com.brycehan.cloud.common.core.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum CaptchaType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    @JsonValue
    private final String value;

    @DescValue
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static CaptchaType getByDesc(String desc) {
        for (CaptchaType captchaType : values()) {
            if (captchaType.getDesc().equals(desc)) {
                return captchaType;
            }
        }
        return null;
    }

}
