package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.DataScopeType;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
public class SysRoleDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 数据范围（0：全部数据，1：自定义数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据）
     */
    @Schema(description = "数据范围（0：全部数据，1：自定义数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据）")
    private DataScopeType dataScope;

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
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 机构IDs
     */
    @Schema(description = "机构IDs")
    private List<Long> orgIds;

    /**
     * 菜单IDs
     */
    @Schema(description = "菜单IDs")
    private List<Long> menuIds;

}
