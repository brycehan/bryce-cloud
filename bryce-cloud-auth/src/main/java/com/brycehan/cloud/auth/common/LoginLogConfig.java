package com.brycehan.cloud.auth.common;

import com.brycehan.cloud.common.core.constant.MQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录日志配置
 *
 * @author Bryce Han
 * @since 2025/2/10
 */
@Configuration
public class LoginLogConfig {

    /**
     * 登录日志事件交换机
     *
     * @return {@link TopicExchange}
     */
    @Bean
    public TopicExchange loginLogExchange() {
        return ExchangeBuilder.topicExchange(MQConstants.LOGIN_LOG_EXCHANGE).build();
    }

    /**
     * 登录日志创建队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue loginLogCreateQueue() {
        return QueueBuilder.durable(MQConstants.LOGIN_LOG_CREATE_QUEUE).build();
    }

    /**
     * 登录日志创建队列绑定
     *
     * @return {@link Binding}
     */
    @Bean
    public Binding loginLogCreateBinding() {
        return BindingBuilder.bind(loginLogCreateQueue()).to(loginLogExchange()).with(MQConstants.LOGIN_LOG_CREATE_ROUTING_KEY);
    }
}
