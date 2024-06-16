package com.brycehan.cloud.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum OperationStatus {

    /** 操作成功 */
    SUCCESS(true),
    /** 操作失败 */
    FAIL(false);
    /** 操作状态值 */

    private final Boolean value;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    public Boolean value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OperationStatus getByValue(Boolean value) {
        for (OperationStatus operationStatus : values()) {
            if (operationStatus.value == value) {
                return operationStatus;
            }
        }
        return null;
    }

}
