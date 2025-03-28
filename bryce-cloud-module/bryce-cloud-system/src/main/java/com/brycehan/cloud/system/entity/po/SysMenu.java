package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.entity.BaseEntity;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.enums.VisibleType;
import com.brycehan.cloud.system.common.MenuType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单entity
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_menu")
public class SysMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 类型（C：目录，M：菜单，B：按钮）
     */
    private MenuType type;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 组件路径
     */
    private String url;

    /**
     * 权限标识
     */
    private String authority;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 打开方式（0：内部，1：外部）
     */
    private Boolean openStyle;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 可见性类型
     */
    private VisibleType visible;

    /**
     * 菜单状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 子菜单列表
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

}
