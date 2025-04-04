package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据范围
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum DataScopeType {

    ALL(0, "全部数据"),
    CUSTOM(1, "自定义数据"),
    ORG_AND_CHILDREN(2, "本部门及以下部门数据"),
    ORG_ONLY(3, "本部门数据"),
    SELF(4, "本人数据"),
    ;

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    @DescValue
    private final String desc;

    /**
     * 根据类型值获取枚举
     *
     * @param value 类型值
     * @return 枚举
     */
    public static DataScopeType of(Integer value) {
        for (DataScopeType dataScopeType : DataScopeType.values()) {
            if (dataScopeType.getValue().equals(value)) {
                return dataScopeType;
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
    public static DataScopeType getByDesc(String desc) {
        for (DataScopeType dataScopeType : DataScopeType.values()) {
            if (dataScopeType.getDesc().equals(desc)) {
                return dataScopeType;
            }
        }
        return null;
    }

}
