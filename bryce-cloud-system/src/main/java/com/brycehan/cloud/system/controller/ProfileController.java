package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.base.dto.ProfileDto;
import com.brycehan.cloud.common.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.base.http.UserResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;
import com.brycehan.cloud.framework.operatelog.annotation.OperateLog;
import com.brycehan.cloud.framework.operatelog.annotation.OperateType;
import com.brycehan.cloud.framework.security.JwtTokenProvider;
import com.brycehan.cloud.framework.security.context.LoginUser;
import com.brycehan.cloud.framework.security.context.LoginUserContext;
import com.brycehan.cloud.system.dto.SysUserPasswordDto;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 用户个人中心API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Tag(name = "用户个人中心", description = "profile")
@RequestMapping(path = "/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final SysUserService sysUserService;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<Map<String, Object>> profile() {
        Long userId = LoginUserContext.currentUserId();
        SysUser sysUser = this.sysUserService.getById(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", sysUser);
        result.put("roleGroup", this.sysUserService.selectUserRoleGroup(sysUser.getUsername()));
        result.put("postGroup", this.sysUserService.selectUserPostGroup(sysUser.getUsername()));
        return ResponseResult.ok(result);
    }

    /**
     * 修改用户个人信息
     *
     * @param profile 用户个人信息
     * @return 响应结果
     */
    @OperateLog(type = OperateType.UPDATE)
    @PatchMapping
    public ResponseResult<Void> update(@RequestBody ProfileDto profile) {
        // 1、校验
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;
        SysUser sysUser = this.sysUserService.getById(loginUser.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(profile, user);
        user.setId(loginUser.getId());
        // 校验手机号码
        if (!this.sysUserService.checkPhoneUnique(user)) {
            return ResponseResult.error(UserResponseStatus.USER_PROFILE_PHONE_INVALID, null, loginUser.getUsername());
        }
        // 校验邮箱
        if (!this.sysUserService.checkEmailUnique(user)) {
            return ResponseResult.error(UserResponseStatus.USER_PROFILE_EMAIL_INVALID, null, loginUser.getUsername());
        }
        // 2、更新并更新缓存用户信息
        if (this.sysUserService.updateById(user)) {
            // 更新缓存用户信息
            BeanUtils.copyProperties(profile, sysUser);
            this.jwtTokenProvider.setLoginUser(loginUser);
            return ResponseResult.ok();
        }

        return ResponseResult.error(UserResponseStatus.USER_PROFILE_ALTER_ERROR);
    }

    /**
     * 头像上传
     *
     * @return 响应结果
     */
    @OperateLog(type = OperateType.UPDATE)
    @Operation(summary = "头像上传")
    @PostMapping(path = "/avatar")
    public ResponseResult<String> avatar(MultipartFile file) throws IOException {
        // 1、参数权限校验
        if (Objects.isNull(file)) {
            return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST);
        }

        // 2、上传文件
        LoginUser loginUser = LoginUserContext.currentUser();
//        SysUser sysUser = loginUser.getSysUser();
//        String avatar = FileUploadUtils.upload(ApplicationProperties.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
//        // 3、更新头像
//        if (this.sysUserService.updateUserAvatar(sysUser.getId(), avatar)) {
//            // 4、更新缓存用户头像
//            loginUser.getSysUser().setAvatar(avatar);
//            this.jwtTokenProvider.setLoginUser(loginUser);
//            return ResponseResult.ok(avatar);
//        }

        return ResponseResult.error(500, "上传图片异常，请联系管理员");
    }

    /**
     * 修改密码
     *
     * @return 响应结果
     */
    @Operation(summary = "修改密码")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping(path = "/password")
    public ResponseResult<String> updatePassword(@Validated @RequestBody SysUserPasswordDto sysUserPasswordDto) {
        // 1、校验密码
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;
        if (!this.passwordEncoder.matches(sysUserPasswordDto.getPassword(), loginUser.getPassword())) {
            throw BusinessException.responseStatus(UserResponseStatus.USER_PASSWORD_NOT_MATCH);
        }
        if (this.passwordEncoder.matches(sysUserPasswordDto.getNewPassword(), loginUser.getPassword())) {
            throw BusinessException.responseStatus(UserResponseStatus.USER_PASSWORD_SAME_AS_OLD_ERROR);
        }

        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getId());
        sysUser.setPassword(this.passwordEncoder.encode(sysUserPasswordDto.getNewPassword()));
        // 2、更新密码
        if (this.sysUserService.updateById(sysUser)) {
            // 3、更新缓存用户信息
            loginUser.setPassword(sysUser.getPassword());
            this.jwtTokenProvider.setLoginUser(loginUser);
            return ResponseResult.ok();
        }

        return ResponseResult.error(UserResponseStatus.USER_PASSWORD_CHANGE_ERROR);
    }

}
