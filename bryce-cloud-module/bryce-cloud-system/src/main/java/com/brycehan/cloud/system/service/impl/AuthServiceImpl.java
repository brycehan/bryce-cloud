package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.brycehan.cloud.common.util.IpUtils;
import com.brycehan.cloud.common.util.ServletUtils;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.service.AuthService;
import com.brycehan.cloud.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Slf4j
@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService sysUserService;

    @Override
    public void updateLoginInfo(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setLastLoginIp(IpUtils.getIp(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", user.getUsername());

        this.sysUserService.update(sysUser, updateWrapper);
    }

}
