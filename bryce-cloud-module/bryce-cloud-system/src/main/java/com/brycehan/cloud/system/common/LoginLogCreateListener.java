package com.brycehan.cloud.system.common;

import com.brycehan.cloud.api.system.entity.dto.SysLoginLogDto;
import com.brycehan.cloud.common.core.constant.MQConstants;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.system.entity.convert.SysLoginLogConvert;
import com.brycehan.cloud.system.entity.po.SysLoginLog;
import com.brycehan.cloud.system.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 登录日志创建监听器
 *
 * @author Bryce Han
 * @since 2025/2/10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginLogCreateListener {

    private final SysLoginLogService sysLoginLogService;

    /**
     * 保存登录日志
     *
     * @param loginLogDto 登录日志
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = MQConstants.LOGIN_LOG_EXCHANGE, type = "topic"),
            key = MQConstants.LOGIN_LOG_CREATE_ROUTING_KEY,
            value = @Queue(name = MQConstants.LOGIN_LOG_CREATE_QUEUE)
    ))
    public void save(String loginLogDto) {
        SysLoginLogDto sysLoginLogDto = JsonUtils.readValue(loginLogDto, SysLoginLogDto.class);
        SysLoginLog sysLoginLog = SysLoginLogConvert.INSTANCE.convert(sysLoginLogDto);
        log.debug("保存登录日志：{}", sysLoginLog);
        sysLoginLogService.save(sysLoginLog);
    }
}
