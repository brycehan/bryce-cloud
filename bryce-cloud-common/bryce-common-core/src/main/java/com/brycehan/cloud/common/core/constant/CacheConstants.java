package com.brycehan.cloud.common.core.constant;

/**
 * 缓存常量
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
public class CacheConstants {

    /**
     * 登录用户键
     */
    public static final String LOGIN_USER_KEY = "login_user:";

    /**
     * 图片验证码键
     */
    public static final String CAPTCHA_CODE_KEY = "sys:captcha:";

    /**
     * 短信验证码过期时间
     */
    public static final long SMS_CODE_TTL = 5L;

    /**
     * 登录短信验证码键
     */
    public static final String SMS_CODE_KEY = "sms:code:";

    /**
     * 短信发送量键
     */
    public static final String SMS_COUNT_KEY = "sms:count:";

    /**
     * 登录账户密码错误次数键
     */
    public static final String PASSWORD_ERROR_COUNT_KEY = "account:password_error_count:";

    /**
     * 缓存空值存活时间
     */
    public static final long CACHE_NULL_TTL = 2L;

    /**
     * 锁键
     */
    public static final String LOCK_KEY = "lock:";

    /**
     * 获取第三方登录 key
     */
    public static final String SYSTEM_THIRD_LOGIN_KEY = "sys:third_login:";

    /**
     * 系统参数键
     */
    public static final String SYSTEM_PARAM_KEY = "sys:param";

    /**
     * 系统操作日志键
     */
    public static final String SYSTEM_OPERATE_LOG_KEY = "sys:log";

    /**
     * workerId分布式锁
     */
    public static final String WORKER_ID_LOCK = LOCK_KEY + "worker_id";

    /**
     * 系统字典翻译键
     */
    public static final String SYSTEM_DICT_TRANS_KEY = "sys:dict:trans:";

    /**
     * 系统字典反向翻译键
     */
    public static final String SYSTEM_DICT_UN_TRANS_KEY = "sys:dict:un_trans:";

}
