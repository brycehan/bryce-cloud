package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 是/否类型
 *
 * @author Bryce Han
 * @since 2024/11/18
 */
@Getter
public enum YesNoType {

    YES("Y", "是"),
    NO("N", "否");

    @JsonValue
    @EnumValue
    private final String value;
    private final String desc;

    YesNoType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
