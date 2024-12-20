package com.brycehan.cloud.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.base.validator.NotEmptyElements;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.util.ExcelUtils;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.dto.*;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysRoleVo;
import com.brycehan.cloud.system.entity.vo.SysUserVo;
import com.brycehan.cloud.system.service.SysRoleService;
import com.brycehan.cloud.system.service.SysUserRoleService;
import com.brycehan.cloud.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 系统用户API
 *
 * @since 2022/05/14
 * @author Bryce Han
 */
@Tag(name = "系统用户")
@Validated
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;


    /**
     * 保存系统用户
     *
     * @param sysUserDto 系统用户Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统用户")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:user:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysUserDto sysUserDto) {
        this.sysUserService.save(sysUserDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统用户
     *
     * @param sysUserDto 系统用户Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统用户")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:user:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysUserDto sysUserDto) {
        this.sysUserService.update(sysUserDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统用户
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统用户")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:user:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        if (CollectionUtil.contains(idsDto.getIds(), LoginUserContext.currentUserId())) {
            throw new RuntimeException("当前用户不能删除");
        }

        this.sysUserService.delete(idsDto);

        return ResponseResult.ok();
    }

    /**
     * 查询系统用户详情
     *
     * @param id 系统用户ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统用户详情")
    @PreAuthorize("@auth.hasAuthority('system:user:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysUserVo> get(@Parameter(description = "系统用户ID", required = true) @PathVariable Long id) {
        SysUserVo sysUserVo = this.sysUserService.get(id);
        return ResponseResult.ok(sysUserVo);
    }

    /**
     * 系统用户分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 系统用户分页列表
     */
    @Operation(summary = "系统用户分页查询")
    @PreAuthorize("@auth.hasAuthority('system:user:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysUserVo>> page(@Validated @RequestBody SysUserPageDto sysUserPageDto) {
        PageResult<SysUserVo> page = this.sysUserService.page(sysUserPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统用户导出数据
     *
     * @param sysUserPageDto 查询条件
     */
    @Operation(summary = "系统用户导出")
    @PreAuthorize("@auth.hasAuthority('system:user:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysUserPageDto sysUserPageDto) {
        this.sysUserService.export(sysUserPageDto);
    }

    /**
     * 下载导入用户模板
     */
    @Operation(summary = "下载导入用户模板")
    @PreAuthorize("@auth.hasAuthority('system:user:import')")
    @GetMapping(path = "/importTemplate")
    public void importTemplate() {
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysUserExcelDto.class, "用户模板_" + today, "用户数据", null);
    }

    /**
     * 导入用户
     *
     * @param file 文件的 Excel 文件
     * @return 响应结果
     */
    @Operation(summary = "导入用户")
    @OperateLog(type = OperatedType.IMPORT)
    @PreAuthorize("@auth.hasAuthority('system:user:import')")
    @PostMapping(path = "/import")
    public ResponseResult<String> importByExcel(@RequestParam MultipartFile file, boolean isUpdateSupport) {
        String message = this.sysUserService.importByExcel(file, isUpdateSupport);
        return ResponseResult.ok(null, message);
    }

    /**
     * 更新系统用户状态
     *
     * @param id     系统用户ID
     * @param status 系统用户状态
     * @return 响应结果
     */
    @Operation(summary = "更新系统用户状态")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:user:update')")
    @PatchMapping(path = "/{id}/{status}")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @PathVariable StatusType status) {
        this.sysUserService.updateStatus(id, status);
        return ResponseResult.ok();
    }

    /**
     * 重置密码
     *
     * @param sysResetPasswordDto 需要重置的用户
     * @return 响应结果
     */
    @Operation(summary = "重置密码")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:user:resetPassword')")
    @PatchMapping(path = "/resetPassword")
    public ResponseResult<Void> resetPassword(@Validated @RequestBody SysResetPasswordDto sysResetPasswordDto) {
        this.sysUserService.resetPassword(sysResetPasswordDto);
        return ResponseResult.ok();
    }

    /**
     * 分配给用户的角色分页查询
     *
     * @param sysAssignRolePageDto 分配角色分页查询Dto
     * @return 角色分页查询
     */
    @Operation(summary = "分配给用户的角色分页查询")
    @PreAuthorize("@auth.hasAuthority('system:user:update')")
    @PostMapping(path = "/assignRole/page")
    public ResponseResult<PageResult<SysRoleVo>> assignRolePage(@Validated @RequestBody SysAssignRolePageDto sysAssignRolePageDto) {
        PageResult<SysRoleVo> page = this.sysRoleService.assignRolePage(sysAssignRolePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 分配给用户多个角色
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     * @return 响应结果
     */
    @Operation(summary = "分配给用户多个角色")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:user:update')")
    @PostMapping(path = "/assignRole/{userId}")
    public ResponseResult<Void> assignRoleSave(@PathVariable Long userId, @RequestBody @Parameter(description = "角色ID集合") @NotEmptyElements List<Long> roleIds) {
        this.sysUserService.checkUserAllowed(SysUser.of(userId));
        this.sysUserService.checkUserDataScope(SysUser.of(userId));
        this.sysRoleService.checkRoleDataScope(roleIds.toArray(Long[]::new));
        this.sysUserRoleService.assignRoleSave(userId, roleIds);
        return ResponseResult.ok();
    }

    /**
     * 删除分配给用户的角色
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     * @return 响应结果
     */
    @Operation(summary = "删除分配给用户的角色")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:user:update')")
    @DeleteMapping(path = "/assignRole/{userId}")
    public ResponseResult<Void> assignRoleDelete(@PathVariable Long userId, @RequestBody @Parameter(description = "角色ID集合") @NotEmptyElements List<Long> roleIds) {
        this.sysUserService.checkUserAllowed(SysUser.of(userId));
        this.sysUserService.checkUserDataScope(SysUser.of(userId));
        this.sysRoleService.checkRoleDataScope(roleIds.toArray(Long[]::new));
        this.sysUserRoleService.deleteByUserIdAndRoleIds(userId, roleIds);
        return ResponseResult.ok();
    }

    /**
     * 校验用户账号是否可注册
     *
     * @param username 用户账号
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户账号是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkUsernameUnique/{username}")
    public ResponseResult<Boolean> checkUsernameUnique(@PathVariable String username) {
        SysUsernameDto sysUsernameDto = new SysUsernameDto();
        sysUsernameDto.setUsername(username);
        boolean checked = this.sysUserService.checkUsernameUnique(sysUsernameDto);
        return ResponseResult.ok(checked);
    }

    /**
     * 校验手机号码是否可注册
     *
     * @param sysUserPhoneDto 手机号码Dto
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验手机号码是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkPhoneUnique")
    public ResponseResult<Boolean> checkPhoneUnique(@Validated SysUserPhoneDto sysUserPhoneDto) {
        boolean checked = this.sysUserService.checkPhoneUnique(sysUserPhoneDto);
        return ResponseResult.ok(checked);
    }

    /**
     * 校验用户邮箱是否可注册
     *
     * @param sysUserEmailDto 用户邮箱Dto
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户邮箱是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkEmailUnique")
    public ResponseResult<Boolean> checkEmailUnique(@Validated SysUserEmailDto sysUserEmailDto) {
        boolean checked = this.sysUserService.checkEmailUnique(sysUserEmailDto);
        return ResponseResult.ok(checked);
    }

}

