package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是/否类型
 *
 * @author Bryce Han
 * @since 2024/11/18
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum YesNoType {

    YES("Y", "是"),
    NO("N", "否");

    /**
     * 类型值
     */
    @JsonValue
    @EnumValue
    private final String value;

    /**
     * 描述
     */
    @DescValue
    private final String desc;

    /**
     * 根据值获取描述
     *
     * @param value 值
     * @return 描述
     */
    public static String getDesc(String value) {
        for (YesNoType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getDesc();
            }
        }
        return null;
    }

    /**
     * 根据描述获取枚举类型
     *
     * @param desc 描述
     * @return 枚举类型
     */
    public static YesNoType getByDesc(String desc) {
        for (YesNoType type : values()) {
            if (type.getDesc().equals(desc)) {
                return type;
            }
        }
        return null;
    }

}
