package com.brycehan.cloud.system.security.service;

import com.brycehan.cloud.common.core.util.MessageUtils;
import com.brycehan.cloud.system.convert.SysUserConvert;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.mapper.SysUserMapper;
import com.brycehan.cloud.system.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务
 *
 * @author Bryce Han
 * @since 2022/5/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    private final SysUserDetailsService sysUserDetailsService;

    /**
     * 获取用户登录信息
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails实例
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        SysUser sysUser = sysUserMapper.getByUsername(username);

        if (sysUser == null) {
            log.debug("loadUserByUsername, 登录用户：{}不存在.", username);
            throw new UsernameNotFoundException(MessageUtils.getMessage("user.username.or.password.error"));
        }

        // 创建用户详情
        return this.sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertLoginUser(sysUser));
    }

}
