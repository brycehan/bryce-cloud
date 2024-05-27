package com.brycehan.cloud.common.core.constant;

/**
 * Jwt常量
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
     * 内部调用header
     */
    public static final String INNER_CALL_HEADER = "inner-call";

    public static final String LOGIN_USER_KEY = "login_user";

    /**
     * 登录用户openid
     */
    public static final String LOGIN_OPEN_ID = "openid";

    /**
     * 刷新最小分钟数
     */
    public static final long REFRESH_LIMIT_MIN_MINUTE = 60L;

}
