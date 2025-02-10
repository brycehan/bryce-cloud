package com.brycehan.cloud.system.common;

import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.common.operatelog.aspect.OperateLogDto;
import com.brycehan.cloud.common.core.constant.MQConstants;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysOperateLogConvert;
import com.brycehan.cloud.system.entity.po.SysOperateLog;
import com.brycehan.cloud.system.service.SysOperateLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 操作日志创建监听器
 *
 * @author Bryce Han
 * @since 2025/2/10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OperateLogCreateListener {

    private final SysOperateLogService sysOperateLogService;

    /**
     * 保存操作日志
     *
     * @param operateLogDto 操作日志
     */
    @RabbitListener(queues = MQConstants.OPERATE_LOG_CREATE_QUEUE)
    public void save(String operateLogDto) {
        // 保存操作日志
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(JsonUtils.readValue(operateLogDto, OperateLogDto.class));
        sysOperateLog.setId(IdGenerator.nextId());
        sysOperateLog.setCreatedTime(LocalDateTime.now());
        log.debug("保存操作日志：{}", sysOperateLog);
        sysOperateLogService.save(sysOperateLog);
    }
}
