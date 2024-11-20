package com.brycehan.cloud.auth.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
public enum CaptchaType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    @JsonValue
    private final String value;
    private final String desc;

    CaptchaType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
