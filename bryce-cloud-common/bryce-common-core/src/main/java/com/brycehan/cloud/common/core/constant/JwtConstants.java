package com.brycehan.cloud.common.core.constant;

/**
 * Jwt 常量
 *
 * @since 2022/5/12
 * @author Bryce Han
 */
public class JwtConstants {

    /**
     * jwt前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户key
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户数据
     */
    public static final String USER_DATA = "user_data";

    /**
     * 登录用户缓存Key
     */
    public static final String LOGIN_USER_KEY = "login_user";

    /**
     * 登录用户openid
     */
    @SuppressWarnings("unused")
    public static final String LOGIN_OPEN_ID = "openid";
}
