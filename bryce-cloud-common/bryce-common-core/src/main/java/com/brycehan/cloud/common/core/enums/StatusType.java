package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据状态枚举
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
public enum StatusType {
    /**
     * 正常
     */
    ENABLE(1),
    /**
     * 停用
     */
    DISABLE(0);

    @JsonValue
    @EnumValue
    private final Integer value;

    StatusType(Integer value) {
        this.value = value;
    }

}
