package com.brycehan.cloud.common.operatelog.config;

import com.brycehan.cloud.common.operatelog.common.MQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志配置
 *
 * @author Bryce Han
 * @since 2025/2/10
 */
@Configuration
public class OperateLogConfig {

    /**
     * 操作日志事件交换机
     *
     * @return {@link TopicExchange}
     */
    @Bean
    public TopicExchange operateLogEventExchange() {
        return ExchangeBuilder.topicExchange(MQConstants.OPERATE_LOG_EVENT_EXCHANGE).build();
    }

    /**
     * 操作日志创建队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue operateLogCreateQueue() {
        return QueueBuilder.durable(MQConstants.OPERATE_LOG_CREATE_QUEUE).build();
    }

    /**
     * 操作日志创建队列绑定
     *
     * @return {@link Binding}
     */
    @Bean
    public Binding operateLogCreateBinding() {
        return BindingBuilder.bind(operateLogCreateQueue()).to(operateLogEventExchange()).with(MQConstants.OPERATE_LOG_CREATE_ROUTING_KEY);
    }
}
