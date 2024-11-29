package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BasePageDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.enums.YesNoType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统角色用户PageDto
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色用户PageDto")
public class SysAssignRolePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long userId;

    /**
     * 是否已分配（N：未分配，Y：已分配）
     */
    @Schema(description = "是否已分配（N：未分配，Y：已分配）")
    private YesNoType assigned;

}
