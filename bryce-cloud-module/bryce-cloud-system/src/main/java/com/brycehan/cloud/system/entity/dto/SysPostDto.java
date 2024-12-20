package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统岗位Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统岗位Dto")
public class SysPostDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @Length(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}
