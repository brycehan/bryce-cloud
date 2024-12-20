package com.brycehan.cloud.system.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单Dto
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统菜单Dto")
public class SysMenuDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 类型（C：目录，M：菜单，B：按钮）
     */
    @Schema(description = "类型（C：目录，M：菜单，B：按钮）")
    @Length(max = 1, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Schema(description = "父菜单ID，一级菜单为0")
    private Long parentId;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String authority;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String icon;

    /**
     * 打开方式（0：内部，1：外部）
     */
    @Schema(description = "打开方式（0：内部，1：外部）")
    private Boolean openStyle;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 子菜单列表
     */
    @Schema(hidden = true)
    @TableField(exist = false)
    private List<SysMenuDto> children = new ArrayList<>();

}
