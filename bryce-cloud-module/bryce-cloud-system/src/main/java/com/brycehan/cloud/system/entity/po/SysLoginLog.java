package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.enums.LoginOperateType;
import com.brycehan.cloud.common.core.enums.OperationStatusType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统登录日志entity
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Data
@TableName("brc_sys_login_log")
public class SysLoginLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 操作信息
     */
    private LoginOperateType info;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 状态（0：失败，1：成功）
     */
    private OperationStatusType status;

    /**
     * 访问时间
     */
    private LocalDateTime accessTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

}
