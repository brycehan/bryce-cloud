package com.brycehan.cloud.common.security.jwt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import com.brycehan.cloud.common.core.util.ServletUtils;
import com.brycehan.cloud.common.security.common.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Jwt令牌工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /**
     * jwt密钥
     */
    @Value("${bryce.auth.jwt.secret}")
    private String jwtSecret = "UZCiSM60eRJMOFA9mbiy";

    /**
     * 令牌过期时间间隔
     */
    @Value("${bryce.auth.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    /**
     * App令牌过期时间间隔
     */
    @Value("${bryce.auth.jwt.app-token-validity-in-days}")
    private long appTokenValidityInDays;

    private final RedisTemplate<String, LoginUser> redisTemplate;

    /**
     * 生成token
     *
     * @param loginUser 登录用户
     * @return 令牌
     */
    public String generateToken(LoginUser loginUser) {
        // 创建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        long expiredInSeconds;

        switch (Objects.requireNonNull(loginUser.getSourceClientType())) {
            case PC, H5 -> {
                loginUser.setUserKey(TokenUtils.uuid());
                claims.put(JwtConstants.USER_KEY, loginUser.getUserKey());
                expiredInSeconds = tokenValidityInSeconds;
            }
            case APP -> {
                claims.put(JwtConstants.USER_DATA, JSONUtil.toJsonStr(loginUser));
                expiredInSeconds = appTokenValidityInDays * 24 * 3600;
            }
            default -> throw new IllegalArgumentException("不支持的来源客户端");
        }

        return generateToken(claims, expiredInSeconds);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @param expiredInSeconds 过期时间秒数
     * @return 令牌
     */
    public String generateToken(Map<String, Object> claims, long expiredInSeconds) {
        // 指定加密方式
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        // 过期时间
        Instant expiredTime = Instant.now().plus(expiredInSeconds, ChronoUnit.SECONDS);
        return JWT.create()
                .withExpiresAt(expiredTime)
                .withPayload(claims)
                // 签发 JWT
                .sign(algorithm);
    }

    /**
     * 获取生成token时的过期时间秒数
     *
     * @param loginUser 登录用户
     * @return 过期时间秒数
     */
    public long getExpiredInSeconds(LoginUser loginUser) {
        long expiredInSeconds;

        switch (Objects.requireNonNull(loginUser.getSourceClientType())) {
            case PC, H5 -> expiredInSeconds = tokenValidityInSeconds;
            case APP -> expiredInSeconds = appTokenValidityInDays * 24 * 3600;
            default -> throw new IllegalArgumentException("不支持的来源客户端");
        }

        return expiredInSeconds;
    }

    /**
     * 缓存登录用户
     *
     * @param loginUser 登录用户
     */
    public void cache(LoginUser loginUser) {
        // 来源客户端
        SourceClientType sourceClientType = loginUser.getSourceClientType();

        LocalDateTime now = LocalDateTime.now();
        // 设置过期时间
        LocalDateTime expireTime = null;
        switch (Objects.requireNonNull(sourceClientType)) {
            case PC, H5 -> expireTime = now.plusSeconds(this.tokenValidityInSeconds);
            case APP -> expireTime = now.plusDays(this.appTokenValidityInDays);
        }

        loginUser.setLoginTime(now);
        loginUser.setExpireTime(expireTime);

        String loginUserKey;
        switch (sourceClientType) {
            case PC, H5 -> {
                loginUserKey = JwtConstants.LOGIN_USER_KEY;
                String cacheUserKey = loginUserKey.concat(":").concat(loginUser.getUserKey());

                // 根据tokenKey将loginUser缓存
                this.redisTemplate.opsForValue()
                        .set(cacheUserKey, loginUser, tokenValidityInSeconds, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 令牌自动续期
     *
     * @param loginUser 登录用户
     */
    public void autoRefreshToken(LoginUser loginUser) {
        if (needRefreshToken(loginUser)) {
            doRefreshToken(loginUser);
        }
    }

    /**
     * 令牌续期
     *
     * @param loginUser 登录用户
     */
    public void doRefreshToken(LoginUser loginUser) {
        if (loginUser == null) {
            return;
        }
        // 生成 jwt
        String token = generateToken(loginUser);
        // 缓存 loginUser
        cache(loginUser);
        // 刷新令牌
        refreshToken(token);
    }

    /**
     * 判断是否需要刷新令牌
     *
     * @param loginUser 登录用户
     * @return 是否需要刷新令牌
     */
    public boolean needRefreshToken(LoginUser loginUser) {
        LocalDateTime expireTime = loginUser.getExpireTime();
        LocalDateTime now = LocalDateTime.now();
        // 存储会话的令牌自动续期
        if (StringUtils.isNotEmpty(loginUser.getUserKey())) {
            return expireTime.isAfter(now) && expireTime.isBefore(now.plusMinutes(JwtConstants.REFRESH_CACHE_MIN_MINUTE));
        } else {
            // 非存储会话的令牌自动续期
            return expireTime.isAfter(now) && expireTime.isBefore(now.plusDays(JwtConstants.REFRESH_APP_MIN_DAY));
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param token 新的令牌
     */
    public void refreshToken(String token) {
        HttpServletResponse response = ServletUtils.getResponse();
        if(response != null) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
            // 将 jwt token 添加到响应头
            response.setHeader(HttpHeaders.AUTHORIZATION, JwtConstants.TOKEN_PREFIX.concat(token));
        }
    }

    /**
     * 获取登录用户信息
     *
     * @param userKey 用户key
     * @return 登录用户
     */
    public LoginUser loadLoginUser(String userKey) {
        try {
            String cacheUserKey = JwtConstants.LOGIN_USER_KEY.concat(":").concat(userKey);
            return this.redisTemplate.opsForValue().get(cacheUserKey);
        } catch (Exception e) {
            log.warn("loadLoginUser, 异常：{}", e.getMessage());
        }

        return null;
    }

    /**
     * 删除登录用户
     *
     * @param userKey 会话存储key
     */
    public void deleteLoginUser(String userKey) {
        if (StrUtil.isNotBlank(userKey)) {
            String loginUserKey = CacheConstants.LOGIN_USER_KEY.concat(userKey);
            this.redisTemplate.delete(loginUserKey);
        }
    }

}
