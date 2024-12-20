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
 * 系统字典类型Dto
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统字典类型Dto")
public class SysDictTypeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictType;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（false：停用，true：正常）
     */
    @Schema(description = "状态（false：停用，true：正常）")
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}
