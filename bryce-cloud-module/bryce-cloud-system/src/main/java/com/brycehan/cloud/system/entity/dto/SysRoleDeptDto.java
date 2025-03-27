package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.DataScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统角色Dto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色Dto")
public class SysRoleDeptDto extends BaseDto {

    /**
     * ID
     */
    @NotNull
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 数据范围
     */
    @Schema(description = "数据范围")
    @NotNull
    private DataScopeType dataScope;

    /**
     * 部门IDs
     */
    @Schema(description = "部门IDs")
    private List<Long> deptIds;

}
