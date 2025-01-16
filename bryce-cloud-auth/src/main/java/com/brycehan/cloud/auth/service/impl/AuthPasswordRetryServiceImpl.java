package com.brycehan.cloud.auth.service.impl;

import com.brycehan.cloud.auth.service.AuthPasswordRetryService;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 密码重试服务实现
 *
 * @since 2022/9/29
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class AuthPasswordRetryServiceImpl implements AuthPasswordRetryService {

    private final RedisTemplate<String, Integer> redisTemplate;

    @Value(value = "${bryce.user.password.lock-duration-minutes}")
    private Integer lockDurationMinutes;

    /**
     * 获取登录账户密码错误次数缓存键
     *
     * @param username 账号
     * @return 缓存键
     */
    private String getPasswordErrorCountCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY.concat(username);
    }

    @Override
    public void retryCount(String username) {
        // 从缓存获取用户账号密码错误次数
        Integer retryCount = redisTemplate.opsForValue().get(getPasswordErrorCountCacheKey(username));

        // 累加次数
        if (retryCount == null) {
            retryCount = 0;
        }

        redisTemplate.opsForValue().set(
                getPasswordErrorCountCacheKey(username),
                ++retryCount,
                lockDurationMinutes,
                TimeUnit.MINUTES
        );
    }

    @Override
    public void deleteCount(String username) {
        redisTemplate.delete(getPasswordErrorCountCacheKey(username));
    }

}
