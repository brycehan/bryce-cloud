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
 * 系统字典数据Dto
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统字典数据Dto")
public class SysDictDataDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictLabel;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictValue;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private Long dictTypeId;

    /**
     * 标签属性
     */
    @Schema(description = "标签属性")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String labelClass;

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
