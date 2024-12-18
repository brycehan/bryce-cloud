package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.entity.BaseEntity;
import com.brycehan.cloud.common.core.entity.vo.RoleVo;
import com.brycehan.cloud.common.core.enums.GenderType;
import com.brycehan.cloud.common.core.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 系统用户entity
 *
 * @author Bryce Han
 * @since 2022/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_user")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别（M：男，F：女，N：未知）
     */
    private GenderType gender;

    /**
     * 用户类型（0：系统用户）
     */
    private Integer type;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账号锁定状态（0：锁定，1：正常）
     */
    private Boolean accountNonLocked;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 拥有角色集合
     */
    @TableField(exist = false)
    private Set<RoleVo> roles;

    /**
     * 拥有角色编码集合
     */
    @TableField(exist = false)
    private Set<String> roleSet;

    /**
     * 权限
     */
    @TableField(exist = false)
    private Set<String> authoritySet;

    /**
     * 是否具有某个角色
     *
     * @param roleKey 角色key
     * @return 是否具有某个角色布尔值
     */
    @SuppressWarnings("unused")
    public boolean hasRole(String roleKey) {
        return !CollectionUtils.isEmpty(this.roleSet) && this.roleSet.contains(roleKey);
    }

    /**
     * 是否是超级管理员
     *
     * @return 是否是超级管理员布尔值
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin(this);
    }

    /**
     * 是否是超级管理员
     *
     * @param sysUser 用户
     * @return 是否是超级管理员布尔值
     */
    public static boolean isSuperAdmin(SysUser sysUser) {
        return sysUser != null && Objects.equals(sysUser.getId(), 1L);
    }

    public static SysUser of(LoginUser loginUser) {
        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getId());
        sysUser.setUsername(loginUser.getUsername());
        sysUser.setNickname(loginUser.getNickname());
        sysUser.setAvatar(loginUser.getAvatar());
        sysUser.setGender(loginUser.getGender());
        sysUser.setPhone(loginUser.getPhone());
        sysUser.setEmail(loginUser.getEmail());
        sysUser.setStatus(loginUser.getStatus());
        sysUser.setRoles(loginUser.getRoles());
        sysUser.setAuthoritySet(loginUser.getAuthoritySet());
        return sysUser;
    }

    public static SysUser of(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        return sysUser;
    }
}
