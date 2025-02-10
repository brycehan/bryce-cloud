package com.brycehan.cloud.common.core.constant;

/**
 * MQ常量
 *
 * @author Bryce Han
 * @since 2025/2/10
 */
public class MQConstants {

    /**
     * 登录日志交换机
     */
    public static final String LOGIN_LOG_EXCHANGE = "login.log.exchange";

    /**
     * 登录日志队列
     */
    public static final String LOGIN_LOG_CREATE_QUEUE = "login.log.create.queue";

    /**
     * 登录日志路由键
     */
    public static final String LOGIN_LOG_CREATE_ROUTING_KEY = "login.log.create";

    /**
     * 操作日志交换机
     */
    public static final String OPERATE_LOG_EXCHANGE = "operate.log.exchange";

    /**
     * 操作日志队列
     */
    public static final String OPERATE_LOG_CREATE_QUEUE = "operate.log.create.queue";

    /**
     * 操作日志路由键
     */
    public static final String OPERATE_LOG_CREATE_ROUTING_KEY = "operate.log.create";
}
