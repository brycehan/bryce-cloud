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
    public static final String CAPTCHA_CODE_KEY = "system:captcha:";

    /**
     * 登录短信验证码键
     */
    public static final String SMS_CODE_KEY = "sms_code:";

    /**
     * 短信发送量键
     */
    public static final String SMS_COUNT_KEY = "sms_count:";

    /**
     * 登录账户密码错误次数键
     */
    public static final String PASSWORD_ERROR_COUNT_KEY = "account:password_error_count:";

    /**
     * 获取第三方登录 key
     */
    public static final String SYSTEM_THIRD_LOGIN_KEY = "system:third_login:";

    /**
     * 系统参数键
     */
    public static final String SYSTEM_PARAM_KEY = "system:param";

    /**
     * 系统操作日志键
     */
    public static final String SYSTEM_OPERATE_LOG_KEY = "system:log";

    /**
     * workerId分布式锁
     */
    public static final String WORKER_ID_LOCK = "worker_id_lock";

}
