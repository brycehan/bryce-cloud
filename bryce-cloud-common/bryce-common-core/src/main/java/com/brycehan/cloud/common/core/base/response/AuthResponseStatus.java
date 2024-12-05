package com.brycehan.cloud.common.core.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 权限响应状态枚举
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthResponseStatus implements ResponseStatus {

    AUTH_ROLE_BLOCKED(1000, "角色已封禁，请联系管理员"),

    AUTH_NO_PERMISSION(1001, "您没有数据的权限，请联系管理员添加权限 [{}]"),

    AUTH_NO_CREATE_PERMISSION(1002, "您没有创建数据的权限，请联系管理员添加权限 [{}]"),

    AUTH_NO_UPDATE_PERMISSION(1003, "您没有修改数据的权限，请联系管理员添加权限 [{}]"),

    AUTH_NO_DELETE_PERMISSION(1004, "您没有删除数据的权限，请联系管理员添加权限 [{}]"),

    AUTH_NO_EXPORT_PERMISSION(1005, "您没有导出数据的权限，请联系管理员添加权限 [{}]"),

    AUTH_NO_VIEW_PERMISSION(1006, "您没有查看数据的权限，请联系管理员添加权限 [{}]");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}
