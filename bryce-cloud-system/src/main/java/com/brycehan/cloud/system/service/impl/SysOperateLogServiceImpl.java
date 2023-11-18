package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.base.RedisKeys;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.common.util.DateTimeUtils;
import com.brycehan.cloud.common.util.ExcelUtils;
import com.brycehan.cloud.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.framework.operatelog.OperateLogDto;
import com.brycehan.cloud.system.convert.SysOperateLogConvert;
import com.brycehan.cloud.system.dto.SysOperateLogPageDto;
import com.brycehan.cloud.system.entity.SysOperateLog;
import com.brycehan.cloud.system.mapper.SysOperateLogMapper;
import com.brycehan.cloud.system.service.SysOperateLogService;
import com.brycehan.cloud.system.vo.SysOperateLogVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 系统操作日志服务实现
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysOperateLogServiceImpl extends BaseServiceImpl<SysOperateLogMapper, SysOperateLog> implements SysOperateLogService {

    private final RedisTemplate<String, OperateLogDto> redisTemplate;

    @Override
    public PageResult<SysOperateLogVo> page(SysOperateLogPageDto sysOperateLogPageDto) {

        IPage<SysOperateLog> page = this.baseMapper.selectPage(getPage(sysOperateLogPageDto), getWrapper(sysOperateLogPageDto));

        return new PageResult<>(page.getTotal(), SysOperateLogConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysOperateLogPageDto 系统操作日志分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysOperateLog> getWrapper(SysOperateLogPageDto sysOperateLogPageDto) {
        LambdaQueryWrapper<SysOperateLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysOperateLogPageDto.getStatus()), SysOperateLog::getStatus, sysOperateLogPageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysOperateLogPageDto.getOrgId()), SysOperateLog::getOrgId, sysOperateLogPageDto.getOrgId());
        wrapper.eq(Objects.nonNull(sysOperateLogPageDto.getTenantId()), SysOperateLog::getTenantId, sysOperateLogPageDto.getTenantId());

        if (sysOperateLogPageDto.getCreatedTimeStart() != null && sysOperateLogPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysOperateLog::getCreatedTime, sysOperateLogPageDto.getCreatedTimeStart(), sysOperateLogPageDto.getCreatedTimeEnd());
        } else if (sysOperateLogPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysOperateLog::getCreatedTime, sysOperateLogPageDto.getCreatedTimeStart());
        } else if (sysOperateLogPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysOperateLog::getCreatedTime, sysOperateLogPageDto.getCreatedTimeEnd());
        }

        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getName()), SysOperateLog::getName, sysOperateLogPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getModuleName()), SysOperateLog::getModuleName, sysOperateLogPageDto.getModuleName());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getRequestUri()), SysOperateLog::getRequestUri, sysOperateLogPageDto.getRequestUri());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getUsername()), SysOperateLog::getUsername, sysOperateLogPageDto.getUsername());
        return wrapper;
    }

    @Override
    public void export(SysOperateLogPageDto sysOperateLogPageDto) {
        List<SysOperateLog> sysOperateLogList = this.baseMapper.selectList(getWrapper(sysOperateLogPageDto));
        List<SysOperateLogVo> sysOperateLogVoList = SysOperateLogConvert.INSTANCE.convert(sysOperateLogList);
        ExcelUtils.export(SysOperateLogVo.class, "系统操作日志_".concat(DateTimeUtils.today()), "系统操作日志", sysOperateLogVoList);
    }

    /**
     * 启动项目时，从Redis队列中获取操作日志关保存
     */
    @PostConstruct
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void handleLog() {
        ScheduledExecutorService scheduledExecutorService = ThreadUtil.createScheduledExecutor(1);

        // 每隔10秒，执行一次
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            List<SysOperateLog> sysOperateLogList = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            // 每次插入1000条
            for (int i = 0; i < 1000; i++) {
                OperateLogDto operateLogDto = this.redisTemplate.opsForList()
                        .rightPop(RedisKeys.getOperateLogKey());
                if (operateLogDto == null) {
                    break;
                }

                SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(operateLogDto);
                sysOperateLog.setId(IdGenerator.nextId());
                sysOperateLog.setCreatedTime(now);

                sysOperateLogList.add(sysOperateLog);
            }

            if (CollectionUtils.isNotEmpty(sysOperateLogList)) {
                this.saveBatch(sysOperateLogList);
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

}
