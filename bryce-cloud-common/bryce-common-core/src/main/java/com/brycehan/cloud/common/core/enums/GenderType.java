package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 性别类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
public enum GenderType {

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

    GenderType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
