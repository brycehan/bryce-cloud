package com.brycehan.cloud.auth.common.security;

import com.brycehan.cloud.api.system.client.SysUserClient;
import com.brycehan.cloud.api.system.entity.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.auth.service.AuthLoginService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import com.brycehan.cloud.common.core.enums.OperateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
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

    private final SysUserClient sysUserClient;
    private final AuthLoginService authLoginService;


    /**
     * 登录成功事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        LoginUser loginUser = (LoginUser) event.getAuthentication().getPrincipal();

        // 记录登录日志
        authLoginService.saveLoginLog(loginUser.getUsername(), OperateStatus.SUCCESS, LoginStatus.LOGIN_SUCCESS);

        // 更新用户登录信息
        SysUserLoginInfoDto sysUserLoginInfoDto = new SysUserLoginInfoDto();
        sysUserLoginInfoDto.setId(loginUser.getId());
        sysUserLoginInfoDto.setLastLoginIp(loginUser.getLoginIp());
        sysUserLoginInfoDto.setLastLoginTime(LocalDateTime.now());
        sysUserClient.updateLoginInfo(sysUserLoginInfoDto);
    }

    /**
     * 登录失败事件处理
     *
     * @param authenticationFailureEvent 认证失败事件
     */
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent authenticationFailureEvent) {
        // 用户名
        String username = (String) authenticationFailureEvent.getAuthentication().getPrincipal();

        // 记录登录日志
        authLoginService.saveLoginLog(username, OperateStatus.FAIL, LoginStatus.ACCOUNT_FAIL);
    }

}
