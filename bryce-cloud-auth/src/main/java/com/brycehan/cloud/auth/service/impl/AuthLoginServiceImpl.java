package com.brycehan.cloud.auth.service.impl;

import com.brycehan.cloud.api.system.api.SysLoginLogApi;
import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.dto.SysLoginLogDto;
import com.brycehan.cloud.api.system.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.auth.security.LoginSuccessEvent;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.auth.service.AuthLoginService;
import com.brycehan.cloud.auth.service.AuthPasswordService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.base.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.base.vo.LoginVo;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.enums.LoginOperateType;
import com.brycehan.cloud.common.core.util.IpUtils;
import com.brycehan.cloud.common.core.util.ServletUtils;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import com.brycehan.cloud.common.security.LoginUserDetailsCheck;
import com.brycehan.cloud.common.security.utils.SecurityUtils;
import com.brycehan.cloud.auth.service.PhoneCodeValidateService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final SysLoginLogApi sysLoginLogApi;

    private final SysUserApi sysUserApi;

    private final AuthCaptchaService authCaptchaService;

    private final AuthPasswordService authPasswordService;

    private final LoginUserDetailsCheck loginUserDetailsCheck;

    private final PhoneCodeValidateService phoneCodeValidateService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public LoginVo loginByAccount(@NotNull AccountLoginDto accountLoginDto) {
        // 校验验证码
        boolean validated = this.authCaptchaService.validate(accountLoginDto.getKey(), accountLoginDto.getCode());
        if (!validated) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(accountLoginDto.getUsername());
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.CAPTCHA_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("验证码错误");
        }

        // IP 黑名单校验

        // 查询用户信息
        ResponseResult<LoginUser> loginUserResponseResult = this.sysUserApi.loadUserByUsername(accountLoginDto.getUsername());
        if (!Objects.equals(loginUserResponseResult.getCode(), HttpResponseStatus.HTTP_OK.code())) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(accountLoginDto.getUsername());
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);

            // 添加密码错误重试缓存
            this.authPasswordService.retryCount(accountLoginDto.getUsername());
            throw new RuntimeException(loginUserResponseResult.getMessage());
        }

        LoginUser loginUser = loginUserResponseResult.getData();
        // 账号已停用
        if (!loginUser.isEnabled()) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(accountLoginDto.getUsername());
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("账号已停用");
        }

        // 校验用户密码错误次数
        UserDetails userDetails = loginUserResponseResult.getData();
        this.loginUserDetailsCheck.check(userDetails);

        // 校验密码
        if (SecurityUtils.matches(accountLoginDto.getPassword(), userDetails.getPassword())) {
            // 清除密码错误重试缓存
            this.authPasswordService.deleteCount(accountLoginDto.getUsername());
        } else {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(accountLoginDto.getUsername());
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("账号不存在/密码错误");
        }

        return getLoginVo(userDetails);
    }

    @Override
    public LoginVo loginByPhone(PhoneLoginDto phoneLoginDto) {
        boolean validated = this.phoneCodeValidateService.validate(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
        if (validated) {
            // 清除密码错误重试缓存
            this.authPasswordService.deleteCount(phoneLoginDto.getPhone());
        } else {
            // 添加密码错误重试缓存
            this.authPasswordService.retryCount(phoneLoginDto.getPhone());
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername("手机号：".concat(phoneLoginDto.getPhone()));
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("手机验证码错误");
        }

        // 查询用户信息
        ResponseResult<LoginUser> loginUserResponseResult = this.sysUserApi.loadUserByPhone(phoneLoginDto.getPhone());
        if (!Objects.equals(loginUserResponseResult.getCode(), HttpResponseStatus.HTTP_OK.code())) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername("手机号：".concat(phoneLoginDto.getPhone()));
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);

            throw new RuntimeException(loginUserResponseResult.getMessage());
        }

        LoginUser loginUser = loginUserResponseResult.getData();
        // 账号已停用
        if (!loginUser.isEnabled()) {
            // 保存登录日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername("手机号：".concat(phoneLoginDto.getPhone()));
            sysLoginLogDto.setStatus(DataConstants.FAIL);
            sysLoginLogDto.setInfo(LoginOperateType.ACCOUNT_FAIL.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
            throw new RuntimeException("账号已停用");
        }

        // 校验用户密码错误次数
        UserDetails userDetails = loginUserResponseResult.getData();

        return getLoginVo(userDetails);
    }

    /**
     * 通过用户登录信息获取登录Vo
     *
     * @param userDetails 用户登录信息
     * @return 登录Vo
     */
    private LoginVo getLoginVo(UserDetails userDetails) {
        LoginUser loginUser = (LoginUser) userDetails;
        // 生成 jwt 令牌
        LoginVo loginVo = this.jwtTokenProvider.generateToken(loginUser);

        // 缓存 loginUser
        this.jwtTokenProvider.cacheLoginUser(loginUser);

        this.updateLoginInfo(loginUser);

        // 发布登录成功事件
        this.applicationEventPublisher.publishEvent(new LoginSuccessEvent(loginUser));
        return loginVo;
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
    public void logout(String accessToken) {
        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(accessToken);

        if (Objects.nonNull(loginUser)) {
            // 删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getTokenKey());

            // 记录用户退出日志
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            sysLoginLogDto.setUsername(loginUser.getUsername());
            sysLoginLogDto.setStatus(DataConstants.SUCCESS);
            sysLoginLogDto.setInfo(LoginOperateType.LOGOUT_SUCCESS.getValue());
            this.sysLoginLogApi.save(sysLoginLogDto);
        }
    }

}
