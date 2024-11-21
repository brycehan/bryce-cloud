package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum OperateStatus implements EnumType {

    SUCCESS(1, "成功"),
    FAIL(0, "失败");

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
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static OperateStatus getByDesc(String desc) {
        for (OperateStatus operateStatus : OperateStatus.values()) {
            if (operateStatus.getDesc().equals(desc)) {
                return operateStatus;
            }
        }
        return null;
    }

}
