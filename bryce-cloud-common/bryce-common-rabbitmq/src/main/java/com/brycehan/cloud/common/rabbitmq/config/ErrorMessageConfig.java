package com.brycehan.cloud.common.rabbitmq.config;

import com.brycehan.cloud.common.rabbitmq.constant.MQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者失败消息处理策略配置
 * <p>
 * 消费消息，在开启重试模式后，重试次数耗尽，如果消息依然失败，将失败消息投递到指定的交换机
 *
 * @author Bryce Han
 * @since 2024/10/30
 */
@Configuration
public class ErrorMessageConfig {

    @Bean
    public DirectExchange errorMessageExchange() {
        return ExchangeBuilder.directExchange(MQConstants.ERROR_DIRECT).build();
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable(MQConstants.ERROR_QUEUE).build();
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(errorMessageExchange()).with(MQConstants.ERROR_ROUTING_KEY);
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, MQConstants.ERROR_DIRECT, MQConstants.ERROR_ROUTING_KEY);
    }
}
