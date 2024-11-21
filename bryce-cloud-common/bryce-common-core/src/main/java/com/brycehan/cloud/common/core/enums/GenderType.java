package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum GenderType implements EnumType {

    MALE("M", "男"),
    FEMALE("F", "女"),
    UNKNOWN("N", "未知");

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据描述获取枚举类型
     *
     * @param desc 描述
     * @return 枚举类型
     */
    public static GenderType getByDesc(String desc) {
        for (GenderType genderType : values()) {
            if (genderType.getDesc().equals(desc)) {
                return genderType;
            }
        }
        return null;
    }

}
