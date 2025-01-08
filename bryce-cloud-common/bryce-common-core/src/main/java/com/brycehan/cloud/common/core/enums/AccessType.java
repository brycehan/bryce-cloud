package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 访问类型
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccessType implements EnumType {

    PUBLIC(0, "公共"),
    SECURE(1, "安全");

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static AccessType getByValue(Integer value) {
        for (AccessType statusType : AccessType.values()) {
            if (statusType.getValue().equals(value)) {
                return statusType;
            }
        }
        return null;
    }

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static AccessType getByDesc(String desc) {
        for (AccessType statusType : AccessType.values()) {
            if (statusType.getDesc().equals(desc)) {
                return statusType;
            }
        }
        return null;
    }

}
