package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 系统登录日志Dto
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统登录日志Dto")
public class SysLoginLogDto extends BaseDto {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 操作信息
     */
    @Schema(description = "操作信息")
    private LoginStatus info;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    @Length(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String ip;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String location;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String os;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String userAgent;

    /**
     * 状态（0：失败，1：成功）
     */
    @Schema(description = "状态（0：失败，1：成功）")
    private Integer status;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    private LocalDateTime accessTime;

}
