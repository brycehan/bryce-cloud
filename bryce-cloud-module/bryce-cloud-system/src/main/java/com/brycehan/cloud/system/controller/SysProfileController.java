package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.entity.dto.SysUserInfoDto;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.dto.SysUserPasswordDto;
import com.brycehan.cloud.system.entity.vo.SysUserInfoVo;
import com.brycehan.cloud.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户个人中心API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "用户个人中心")
@RequestMapping(path = "/profile")
@RestController
@Validated
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
    public ResponseResult<SysUserInfoVo> getUserInfo() {
        Long userId = LoginUserContext.currentUserId();
        SysUserInfoVo sysUserInfoVo = sysUserService.getUserInfo(userId);
        return ResponseResult.ok(sysUserInfoVo);
    }

    /**
     * 修改用户个人信息
     *
     * @param sysUserInfoDto 用户个人信息
     * @return 响应结果
     */
    @Operation(summary = "修改用户个人信息")
    @OperateLog(type = OperatedType.UPDATE)
    @PutMapping
    public ResponseResult<Void> updateUserInfo(@RequestBody SysUserInfoDto sysUserInfoDto) {
        sysUserService.updateUserInfo(sysUserInfoDto);
        return ResponseResult.ok();
    }

    /**
     * 修改用户头像
     *
     * @param file 用户头像文件
     * @return 响应结果
     */
    @Operation(summary = "修改用户头像")
    @OperateLog(type = OperatedType.UPDATE)
    @PostMapping(path = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> updateAvatar(@NotNull MultipartFile file) {
        String avatar = sysUserService.updateAvatar(file);
        return ResponseResult.ok(avatar);
    }

    /**
     * 修改密码
     *
     * @param sysUserPasswordDto 系统用户修改密码Dto
     * @return 响应结果
     */
    @Operation(summary = "修改密码")
    @OperateLog(type = OperatedType.UPDATE)
    @PutMapping(path = "/password")
    public ResponseResult<Void> updatePassword(@Validated @RequestBody SysUserPasswordDto sysUserPasswordDto) {
        sysUserService.updatePassword(sysUserPasswordDto);
        return ResponseResult.ok();
    }

}
