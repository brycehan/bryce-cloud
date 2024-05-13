package com.brycehan.cloud.system.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 认证服务
 *
 * @since 2022/9/16
 * @author Bryce Han
 */

public interface AuthService {

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户
     */
    void updateLoginInfo(UserDetails loginUser);

}
