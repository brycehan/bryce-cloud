package com.brycehan.cloud.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通知状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
public enum NoticeStatus {

    OFF(0, "关闭"),
    ON(1, "正常"),
    ;

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

    NoticeStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}