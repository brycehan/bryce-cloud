package com.brycehan.cloud.auth.security.service.impl;

import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.base.http.UserResponseStatus;
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

    private final SysUserApi sysUserApi;

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
        ResponseResult<LoginUser> sysUserResponseResult = sysUserApi.loadUserByUsername(username);

        if (sysUserResponseResult.getCode() != 200) {
            log.error("查询用户[{}]异常，{}.", username, sysUserResponseResult.getMessage());
            throw new UsernameNotFoundException(sysUserResponseResult.getMessage());
        }

        LoginUser loginUser = sysUserResponseResult.getData();

        if (loginUser == null) {
            log.debug("登录用户：{}不存在.", username);
            throw new UsernameNotFoundException("账号或密码错误");
        }

        if (!loginUser.isEnabled()) {
            log.info("登录用户：{}已被锁定.", loginUser.getUsername());
            throw new ServerException(UserResponseStatus.USER_ACCOUNT_LOCKED);
        }

        // 用户详情
        return loginUser;
    }

}
