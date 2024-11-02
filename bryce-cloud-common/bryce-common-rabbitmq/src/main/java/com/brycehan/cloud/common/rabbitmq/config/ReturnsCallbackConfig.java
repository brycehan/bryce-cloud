package com.brycehan.cloud.common.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 消息路由失败回调配置
 *
 * @author Bryce Han
 * @since 2024/10/28
 */
@Configuration(proxyBeanMethods = false)
public class ReturnsCallbackConfig implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ReturnsCallbackConfig.class);

    private final RabbitTemplate rabbitTemplate;

    public ReturnsCallbackConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 配置消息路由失败回调
     */
    @Override
    public void afterPropertiesSet() {
        // 只有不可达目的地的时候 才进行回退
        rabbitTemplate.setReturnsCallback(returned -> {
            // 判断是否是延迟消息
            Long receivedDelayLong = returned.getMessage().getMessageProperties().getReceivedDelayLong();
            if (receivedDelayLong != null && receivedDelayLong > 0) {
                // 是一个延迟消息，忽略这个错误提示
                return;
            }

            log.error("消息投递到队列失败，响应码: {}，退回原因：{}，交换机：{}，路由键：{}，消息：{}",
                    returned.getReplyCode(), returned.getReplyText(), returned.getExchange(), returned.getRoutingKey(), returned.getMessage().toString());
        });
    }

}
