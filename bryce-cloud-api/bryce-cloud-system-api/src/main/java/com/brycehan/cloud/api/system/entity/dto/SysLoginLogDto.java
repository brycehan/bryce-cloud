package com.brycehan.cloud.api.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import com.brycehan.cloud.common.core.enums.OperateStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统登录日志Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLoginLogDto extends BaseDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 操作信息
     */
    private LoginStatus info;

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
    private OperateStatus status;

    /**
     * 访问时间
     */
    private LocalDateTime accessTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
