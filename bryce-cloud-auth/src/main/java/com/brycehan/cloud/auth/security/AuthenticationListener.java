package com.brycehan.cloud.auth.security;

import com.brycehan.cloud.api.system.SysLoginLogApi;
import com.brycehan.cloud.api.system.SysUserApi;
import com.brycehan.cloud.api.system.dto.SysLoginLogDto;
import com.brycehan.cloud.api.system.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.enums.LoginOperateType;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 认证事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Component
@RequiredArgsConstructor
public class AuthenticationListener {

    private final SysLoginLogApi sysLoginLogApi;

    private final SysUserApi sysUserApi;

    /**
     * 登录成功事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onSuccess(LoginSuccessEvent event) {
        // 用户信息
        LoginUser user = (LoginUser) event.getSource();
        // 记录登录日志
        SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
        sysLoginLogDto.setUsername(user.getUsername());
        sysLoginLogDto.setStatus(DataConstants.SUCCESS);
        sysLoginLogDto.setInfo(LoginOperateType.LOGIN_SUCCESS.getValue());
        this.sysLoginLogApi.save(sysLoginLogDto);

        // 更新用户登录信息
        SysUserLoginInfoDto sysUserLoginInfoDto = new SysUserLoginInfoDto();
        sysUserLoginInfoDto.setId(user.getId());
        sysUserLoginInfoDto.setLastLoginIp(user.getLoginIp());
        sysUserLoginInfoDto.setLastLoginTime(LocalDateTime.now());
        this.sysUserApi.updateLoginInfo(sysUserLoginInfoDto);
    }

    /**
     * 登录失败事件处理
     *
     * @param loginFailureEvent 认证失败事件
     */
    @EventListener
    public void onFailure(LoginFailureEvent loginFailureEvent) {
        // 用户信息
//        LoginUser user = (LoginUser) loginFailureEvent.getSource();
//        // 记录登录日志
//        SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
//        sysLoginLogDto.setUsername(user.getUsername());
//        sysLoginLogDto.setStatus(DataConstants.SUCCESS);
//        sysLoginLogDto.setInfo(LoginOperateType.LOGIN_SUCCESS.getValue());
//        this.sysLoginLogApi.save(sysLoginLogDto);
//
//        String username = (String) authenticationFailureEvent.getAuthentication().getPrincipal();
//        // 记录登录日志
//        this.sysLoginLogApi.save(username, CommonConstants.LOGIN_FAIL, LoginOperateType.LOGIN_SUCCESS.getValue());
    }

}
