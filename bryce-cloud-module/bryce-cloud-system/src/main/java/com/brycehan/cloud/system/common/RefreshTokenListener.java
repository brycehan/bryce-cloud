package com.brycehan.cloud.system.common;

import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import com.brycehan.cloud.system.entity.po.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 刷新令牌事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenListener {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 刷新令牌事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onRefreshToken(RefreshTokenEvent event) {
        LoginUser loginUser = LoginUserContext.currentUser();

        if (loginUser == null) {
            return;
        }

        // 用户信息
        SysUser sysUser = event.getSysUser();
        log.info("用户[{}]刷新令牌事件处理...", loginUser.getUsername());

        // 更新用户信息
        loginUser.setNickname(sysUser.getNickname());
        loginUser.setPhone(sysUser.getPhone());
        loginUser.setEmail(sysUser.getEmail());
        loginUser.setGender(sysUser.getGender());
        loginUser.setAvatar(sysUser.getAvatar());

        jwtTokenProvider.doRefreshToken(loginUser);
    }

}
