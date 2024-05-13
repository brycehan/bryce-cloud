package com.brycehan.cloud.auth.service.impl;

import com.brycehan.cloud.api.system.SysParamApi;
import com.brycehan.cloud.api.system.SysUserApi;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.auth.service.AuthRegisterService;
import com.brycehan.cloud.common.base.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 系统注册服务实现类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRegisterServiceImpl implements AuthRegisterService {

    private final SysUserApi sysUserApi;

    private final SysParamApi sysParamService;

    private final PasswordEncoder passwordEncoder;

    private final AuthCaptchaService authCaptchaService;

    @Override
    public void register(RegisterDto registerDto) {
//        // 验证码开关
//        boolean validated = this.validate(registerDto.getKey(), registerDto.getCode());
//        if (!validated) {
//            throw new RuntimeException("验证码错误");
//        }
//
//        // 用户账号唯一校验
//        SysUser sysUser = new SysUser();
//        sysUser.setUsername(registerDto.getUsername().trim());
//        boolean usernameUnique = this.sysUserService.checkUsernameUnique(sysUser);
//        if (!usernameUnique) {
//            throw new ServerException(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
//        }
//
//        // 注册
//        sysUser.setFullName(sysUser.getUsername());
//        sysUser.setPassword(passwordEncoder.encode(registerDto.getPassword().trim()));
//        this.sysUserService.registerUser(sysUser);
    }

    @Override
    public boolean validate(String key, String code) {
        return this.authCaptchaService.validate(key, code);
    }

    @Override
    public boolean isCaptchaEnabled() {
        return this.sysParamService.getBoolean("system.account.captchaEnabled");
    }

}
