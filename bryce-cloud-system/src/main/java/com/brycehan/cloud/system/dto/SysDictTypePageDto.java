package com.brycehan.cloud.system.dto;

import com.brycehan.cloud.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统字典类型PageDto
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Schema(description = "系统字典类型PageDto")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictTypePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @Size(max = 100)
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @Size(max = 100)
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
    private Boolean status;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

}
