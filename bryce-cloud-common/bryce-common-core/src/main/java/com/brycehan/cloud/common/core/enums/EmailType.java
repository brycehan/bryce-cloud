package com.brycehan.cloud.common.core.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 邮件类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum EmailType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册账号");

    private final String value;
    private final String desc;

    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static EmailType getByValue(String value) {
        for (EmailType emailType : values()) {
            if (emailType.value.equals(value)) {
                return emailType;
            }
        }
        return null;
    }

}
