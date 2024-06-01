package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.dto.ProfileDto;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.common.security.context.LoginUserContext;
import com.brycehan.cloud.system.entity.dto.SysUserPasswordDto;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<Map<String, Object>> getUserInfo() {
        Long userId = LoginUserContext.currentUserId();
        SysUser sysUser = this.sysUserService.getById(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", sysUser);

        return ResponseResult.ok(result);
    }

    /**
     * 修改用户个人信息
     *
     * @param profileDto 用户个人信息
     * @return 响应结果
     */
    @Operation(summary = "修改用户个人信息")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping
    public ResponseResult<Void> updateUserInfo(@RequestBody ProfileDto profileDto) {
        this.sysUserService.updateUserInfo(profileDto);
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
