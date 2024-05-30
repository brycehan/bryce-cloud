package com.brycehan.cloud.auth.service.impl;

import com.brycehan.cloud.api.system.api.SysLoginLogApi;
import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.dto.SysLoginLogDto;
import com.brycehan.cloud.api.system.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.auth.common.CaptchaType;
import com.brycehan.cloud.auth.security.PhoneCodeAuthenticationToken;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.auth.service.AuthLoginService;
import com.brycehan.cloud.auth.service.AuthPasswordRetryService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.base.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.base.vo.LoginVo;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.enums.LoginOperateType;
import com.brycehan.cloud.common.core.util.IpUtils;
import com.brycehan.cloud.common.core.util.ServletUtils;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLoginServiceImpl implements AuthLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final SysUserApi sysUserApi;
    private final SysLoginLogApi sysLoginLogApi;
    private final AuthCaptchaService authCaptchaService;
    private final AuthPasswordRetryService authPasswordRetryService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginVo loginByAccount(@NotNull AccountLoginDto accountLoginDto) {
        // 校验验证码
        boolean validated = this.authCaptchaService.validate(accountLoginDto.getKey(), accountLoginDto.getCode(), CaptchaType.LOGIN);
        if (!validated) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(accountLoginDto.getUsername());
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.CAPTCHA_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("验证码错误");
        }

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword()));
        } catch (AuthenticationException e) {
            log.info("登录认证失败，{}", e.getMessage());
            // 添加密码错误重试缓存
            this.authPasswordRetryService.retryCount(accountLoginDto.getUsername());
            throw new RuntimeException("用户名或密码错误");
        }

        // 清除密码错误重试缓存
        this.authPasswordRetryService.deleteCount(accountLoginDto.getUsername());

        return loadLoginVo(authentication);
    }

    @Override
    public LoginVo loginByPhone(PhoneLoginDto phoneLoginDto) {
        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("认证失败，{}", e.getMessage());
            throw new RuntimeException("手机号或验证码错误");
        }

        return loadLoginVo(authentication);
    }

    /**
     * 通过认证信息获取登录 Vo
     *
     * @param authentication 认证信息
     * @return 登录 Vo
     */
    private LoginVo loadLoginVo(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成 jwt
        String token = this.jwtTokenProvider.generateToken(loginUser);

        // 缓存 loginUser
        this.jwtTokenProvider.cacheLoginUser(loginUser);

        LoginVo userLoginVo = new LoginVo();
        BeanUtils.copyProperties(loginUser, userLoginVo);
        userLoginVo.setNickname(loginUser.getFullName());
        userLoginVo.setToken(JwtConstants.TOKEN_PREFIX.concat(token));

        return userLoginVo;

    }

    @Override
    public void updateLoginInfo(LoginUser loginUser) {
        SysUserLoginInfoDto sysUser = new SysUserLoginInfoDto();
        sysUser.setId(loginUser.getId());
        sysUser.setLastLoginIp(IpUtils.getIp(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());

        this.sysUserApi.updateLoginInfo(sysUser);
    }

    @Override
    public void logout(LoginUser loginUser) {

        if (Objects.isNull(loginUser)) {
            return;
        }

        if (StringUtils.isNotEmpty(loginUser.getUserKey())) {
            // 删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getUserKey());
        }

        // 记录用户退出日志
        SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
        sysLoginLogDto.setUsername(loginUser.getUsername());
        sysLoginLogDto.setStatus(DataConstants.SUCCESS);
        sysLoginLogDto.setInfo(LoginOperateType.LOGOUT_SUCCESS.getValue());
        this.sysLoginLogApi.save(sysLoginLogDto);
    }

}
