package com.brycehan.cloud.system.dto;

import com.brycehan.cloud.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统机构PageDto
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Schema(description = "系统机构PageDto")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysOrgPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    @Size(max = 100)
    private String name;

}
