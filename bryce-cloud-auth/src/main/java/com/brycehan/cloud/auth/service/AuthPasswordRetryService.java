package com.brycehan.cloud.auth.service;

/**
 * 密码重试服务
 *
 * @since 2022/9/29
 * @author Bryce Han
 */
public interface AuthPasswordRetryService {

    void validate(String username, String password);

    /**
     * 用户的密码错误次数
     *
     * @param username 用户账号
     */
    void retryCount(String username);

    /**
     * 清除用户的密码错误次数缓存
     *
     * @param username 用户账号
     */
    void deleteCount(String username);

}
