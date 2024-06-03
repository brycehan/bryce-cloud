package com.brycehan.cloud.api.system.entity.dto;

import com.brycehan.cloud.common.core.validator.SaveGroup;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户Dto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@Schema(description = "系统用户Dto")
public class SysUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Size(max = 30, groups = {UpdateGroup.class})
    @Size(min = 6, max = 30, groups = SaveGroup.class, message = "密码长度在6-30个字符")
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String fullName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @Size(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String avatar;

    /**
     * 性别（M：男, F：女，N：未知）
     */
    @Schema(description = "性别（M：男, F：女，N：未知）")
    @Size(max = 1, groups = {SaveGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[MFN]$", groups = {SaveGroup.class, UpdateGroup.class}, message = "性别值只能是M、F、N")
    private String gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Integer type;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 超级管理员
     */
    @Schema(description = "超级管理员")
    private Boolean superAdmin;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 账号锁定状态（0：锁定，1：正常）
     */
    @Schema(description = "账号锁定状态（0：锁定，1：正常）")
    private Boolean accountNonLocked;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @Size(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 角色IDs
     */
    @Schema(description = "角色IDs")
    private List<Long> roleIds;

    /**
     * 岗位IDs
     */
    @Schema(description = "岗位ID")
    private List<Long> postIds;

}
