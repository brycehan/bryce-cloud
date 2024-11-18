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
    /**
     * 男
     */
    MALE("M"),
    /**
     * 女
     */
    FEMALE("F");

    @EnumValue
    @JsonValue
    private final String value;

    GenderType(String value) {
        this.value = value;
    }

}
