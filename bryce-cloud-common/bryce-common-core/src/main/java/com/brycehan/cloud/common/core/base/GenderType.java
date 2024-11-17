package com.brycehan.cloud.common.core.base;

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
@RequiredArgsConstructor
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

}
