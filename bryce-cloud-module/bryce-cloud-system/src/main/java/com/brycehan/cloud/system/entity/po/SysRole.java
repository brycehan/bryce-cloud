package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.entity.BaseEntity;
import com.brycehan.cloud.common.core.enums.DataScopeType;
import com.brycehan.cloud.common.core.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Optional;
import java.util.Set;

/**
 * 系统角色entity
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_role")
public class SysRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）
     */
    private DataScopeType dataScope;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long deptId;
    /**
     * 角色菜单权限
     */
    @TableField(exist = false)
    private Set<String> authoritySet;

    /**
     * 是否为超级管理员
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin(this);
    }

    /**
     * 是否为超级管理员
     */
    public static boolean isSuperAdmin(SysRole sysRole) {
        return Optional.ofNullable(sysRole).map(SysRole::getId).map(id -> id == 1L).orElse(false);
    }

    public static SysRole of(Long id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        return sysRole;
    }
}
