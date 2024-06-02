package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.dto.SysUserAvatarDto;
import com.brycehan.cloud.common.core.base.dto.SysUserInfoDto;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.common.security.context.LoginUserContext;
import com.brycehan.cloud.system.entity.dto.SysUserPasswordDto;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysUserInfoVo;
import com.brycehan.cloud.system.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户个人中心API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Tag(name = "用户个人中心")
@RequestMapping(path = "/profile")
@RestController
@RequiredArgsConstructor
public class SysProfileController {

    private final SysUserService sysUserService;
    private final SysUserPostService sysUserPostService;
    private final SysPostService sysPostService;
    private final SysOrgService sysOrgService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<SysUserInfoVo> getUserInfo() {
        Long userId = LoginUserContext.currentUserId();
        SysUser sysUser = this.sysUserService.getById(userId);

        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        BeanUtils.copyProperties(sysUser, sysUserInfoVo);

        // 机构名称
        sysUserInfoVo.setOrgName(this.sysOrgService.getOrgNameById(sysUser.getOrgId()));

        // 用户岗位名称列表
        List<Long> postIdList = this.sysUserPostService.getPostIdsByUserId(userId);
        List<String> postNameList = this.sysPostService.getPostNameList(postIdList);
        sysUserInfoVo.setPostNameList(String.join(",", postNameList));

        // 用户角色名称列表
        List<Long> roleIdList = this.sysUserRoleService.getRoleIdsByUserId(userId);
        List<String> roleNameList = this.sysRoleService.getRoleNameList(roleIdList);
        if (sysUser.getSuperAdmin()) {
            roleNameList.add("超级管理员");
        }
        sysUserInfoVo.setRoleNameList(String.join(",", roleNameList));

        return ResponseResult.ok(sysUserInfoVo);
    }

    /**
     * 修改用户个人信息
     *
     * @param sysUserInfoDto 用户个人信息
     * @return 响应结果
     */
    @Operation(summary = "修改用户个人信息")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping
    public ResponseResult<Void> updateUserInfo(@RequestBody SysUserInfoDto sysUserInfoDto) {
        this.sysUserService.updateUserInfo(sysUserInfoDto);
        return ResponseResult.ok();
    }

    /**
     * 修改用户头像
     *
     * @param sysUserAvatarDto 用户头像Dto
     * @return 响应结果
     */
    @Operation(summary = "修改用户头像")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping(path = "/avatar")
    public ResponseResult<Void> updateAvatar(@RequestBody SysUserAvatarDto sysUserAvatarDto) {
        this.sysUserService.updateAvatar(sysUserAvatarDto);
        return ResponseResult.ok();
    }

    /**
     * 修改密码
     *
     * @param sysUserPasswordDto 系统用户修改密码 Dto
     * @return 响应结果
     */
    @Operation(summary = "修改密码")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping(path = "/password")
    public ResponseResult<Void> updatePassword(@Validated @RequestBody SysUserPasswordDto sysUserPasswordDto) {
        this.sysUserService.updatePassword(sysUserPasswordDto);
        return ResponseResult.ok();
    }

}
