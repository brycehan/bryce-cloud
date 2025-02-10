package com.brycehan.cloud.common.operatelog.aspect;

import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.common.operatelog.common.MQConstants;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Service
public class OperateLogService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void save(OperateLogDto operateLogDto) {
        rabbitTemplate.convertAndSend(MQConstants.OPERATE_LOG_EVENT_EXCHANGE, MQConstants.OPERATE_LOG_CREATE_ROUTING_KEY, JsonUtils.writeValueAsString(operateLogDto));
    }
}
