package com.brycehan.cloud.common.security.common.jwt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.util.IpUtils;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.common.core.util.LocationUtils;
import com.brycehan.cloud.common.core.util.ServletUtils;
import com.brycehan.cloud.common.core.enums.SourceClientType;
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

        // 设置用户代理
        this.setUserAgent(loginUser);

        // 设置IP地址
        loginUser.setLoginIp(IpUtils.getIp(ServletUtils.getRequest()));

        // 设置来源客户端
        String sourceClient = TokenUtils.getSourceClient(ServletUtils.getRequest());
        SourceClientType sourceClientType = SourceClientType.getByValue(sourceClient);

        // 创建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        long expiredTimeSeconds = 0L;

        switch (Objects.requireNonNull(sourceClientType)) {
            case PC, H5 -> {
                loginUser.setUserKey(TokenUtils.uuid());
                claims.put(JwtConstants.USER_KEY, loginUser.getUserKey());
                expiredTimeSeconds = tokenValidityInSeconds;
            }
            case APP -> {
                claims.put(JwtConstants.USER_DATA, JsonUtils.writeValueAsString(loginUser));
                expiredTimeSeconds = appTokenValidityInDays * 24 * 60 * 60;
            }
        }

        return generateToken(claims, expiredTimeSeconds);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String generateToken(Map<String, Object> claims, long expiredTimeSeconds) {
        // 指定加密方式
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        // 过期时间
        Instant expiredTime = Instant.now().plus(expiredTimeSeconds, ChronoUnit.SECONDS);
        return JWT.create()
                .withExpiresAt(expiredTime)
                .withPayload(claims)
                // 签发 JWT
                .sign(algorithm);
    }

    /**
     * 设置用户代理
     *
     * @param loginUser 登录用户
     */
    private void setUserAgent(LoginUser loginUser) {
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        UserAgent parser = UserAgentUtil.parse(userAgent);

        // 获取客户端浏览器
        String browser = parser.getBrowser().getName();

        // 获取客户端操作系统
        String os = parser.getOs().getName();
        // 获取客户端IP和对应登录位置
        String ip = IpUtils.getIp(ServletUtils.getRequest());
        String loginLocation = LocationUtils.getLocationByIP(ip);

        loginUser.setLoginIp(ip);
        loginUser.setLoginLocation(loginLocation);
        loginUser.setBrowser(browser);
        loginUser.setOs(os);
    }

    public void cacheLoginUser(LoginUser loginUser) {
        // 来源客户端
        String sourceClient = TokenUtils.getSourceClient(ServletUtils.getRequest());
        SourceClientType sourceClientType = SourceClientType.getByValue(sourceClient);

        LocalDateTime now = LocalDateTime.now();
        loginUser.setLoginTime(now);
        // 设置过期时间
        LocalDateTime expireTime = null;
        switch (Objects.requireNonNull(sourceClientType)) {
            case PC, H5 -> expireTime = now.plusSeconds(this.tokenValidityInSeconds);
            case APP -> expireTime = now.plusDays(this.appTokenValidityInDays);
        }
        loginUser.setExpireTime(expireTime);
        loginUser.setSourceClient(sourceClientType.value());

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
     * 令牌自动续期，相差不足60分钟，自动刷新延长登录有效期
     *
     * @param loginUser 登录用户
     */
    public void autoRefreshToken(LoginUser loginUser) {
        // 存储会话的token续期
        if (StringUtils.isNotEmpty(loginUser.getUserKey())) {
            LocalDateTime expireTime = loginUser.getExpireTime();
            LocalDateTime now = LocalDateTime.now();

            if (expireTime.isAfter(now) && expireTime.isBefore(now.plusMinutes(JwtConstants.REFRESH_LIMIT_MIN_MINUTE))) {
                // 生成 jwt
                String token = generateToken(loginUser);
                // 缓存 loginUser
                cacheLoginUser(loginUser);
                // 刷新令牌
                refreshToken(token);
            }
        }
    }

    /**
     * 小程序和App的令牌自动续期，一天一续期，自动刷新延长登录有效期
     *
     * @param claimMap 登录用户
     */
    public void autoRefreshToken(Map<String, Claim> claimMap) {
        Instant exp = claimMap.get("exp").asInstant();
        String openid = claimMap.get("openid").asString();

        if (Instant.now().compareTo(exp.minus(29, ChronoUnit.DAYS)) > 0) {
            // 生成 jwt
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtConstants.LOGIN_OPEN_ID, openid);

            long expiredTimeSeconds = appTokenValidityInDays * 24 * 60 * 60;

            String token = this.generateToken(claims, expiredTimeSeconds);

            ServletUtils.getResponse().setHeader(HttpHeaders.AUTHORIZATION, JwtConstants.TOKEN_PREFIX.concat(token));
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param token 新的令牌
     */
    private void refreshToken(String token) {
        HttpServletResponse response = ServletUtils.getResponse();
        if(response != null) {
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
     * 设置登录用户，修改用户信息时
     *
     * @param loginUser 登录用户
     */
    public void setLoginUser(LoginUser loginUser) {
        if (Objects.nonNull(loginUser) && StringUtils.isNotEmpty(loginUser.getUserKey())) {
            // refreshToken(loginUser);
        }
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
