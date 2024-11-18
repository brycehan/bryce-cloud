package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
public enum OperationStatusType {

    /** 操作成功 */
    SUCCESS(1),
    /** 操作失败 */
    FAIL(0);
    /** 操作状态值 */
    @EnumValue
    @JsonValue
    private final Integer value;

    OperationStatusType(Integer value) {
        this.value = value;
    }

}
