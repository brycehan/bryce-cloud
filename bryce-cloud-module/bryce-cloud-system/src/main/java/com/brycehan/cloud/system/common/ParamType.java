package com.brycehan.cloud.system.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.cloud.common.core.enums.EnumType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 参数类型枚举
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum ParamType implements EnumType {

    SYSTEM(0, "系统"),
    BUILD_IN(1, "内置");

    @JsonValue
    @EnumValue
    private final Integer value;
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static ParamType getByDesc(String desc) {
        for (ParamType paramType : values()) {
            if (paramType.getDesc().equals(desc)) {
                return paramType;
            }
        }
        return null;
    }

}
