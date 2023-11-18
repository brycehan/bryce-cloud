package com.brycehan.cloud.quartz.dto;

import com.brycehan.cloud.common.validator.SaveGroup;
import com.brycehan.cloud.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * quartz 定时任务调度Dto
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz 定时任务调度Dto")
public class QuartzJobDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String jobGroup;

    /**
     * Spring Bean 名称
     */
    @Schema(description = "Spring Bean 名称")
    @Size(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String beanName;

    /**
     * 执行方法
     */
    @Schema(description = "执行方法")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String method;

    /**
     * 参数
     */
    @Schema(description = "参数")
    @Size(max = 2000, groups = {SaveGroup.class, UpdateGroup.class})
    private String params;

    /**
     * cron 表达式
     */
    @Schema(description = "cron 表达式")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String cronExpression;

    /**
     * 是否并发执行（N：否，Y：是）
     */
    @Schema(description = "是否并发执行（N：否，Y：是）")
    private String concurrent;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
