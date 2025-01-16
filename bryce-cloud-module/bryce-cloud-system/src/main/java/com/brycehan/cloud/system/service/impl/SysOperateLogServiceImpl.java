package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.operatelog.aspect.OperateLogDto;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysOperateLogConvert;
import com.brycehan.cloud.system.entity.dto.SysOperateLogDto;
import com.brycehan.cloud.system.entity.dto.SysOperateLogPageDto;
import com.brycehan.cloud.system.entity.po.SysOperateLog;
import com.brycehan.cloud.system.entity.vo.SysOperateLogVo;
import com.brycehan.cloud.system.mapper.SysOperateLogMapper;
import com.brycehan.cloud.system.service.SysOperateLogService;
import com.brycehan.cloud.system.service.SysOrgService;
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
import java.util.Date;
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
    private final SysOrgService sysOrgService;

     /**
     * 添加系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    public void save(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        sysOperateLog.setId(IdGenerator.nextId());
        baseMapper.insert(sysOperateLog);
    }

    /**
     * 更新系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    public void update(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        baseMapper.updateById(sysOperateLog);
    }

    @Override
    public SysOperateLogVo get(Long id) {
        SysOperateLog sysOperateLog = this.getById(id);
        SysOperateLogVo sysOperateLogVo = SysOperateLogConvert.INSTANCE.convert(sysOperateLog);

        // 机构名称
        String orgName = sysOrgService.getOrgNameById(sysOperateLogVo.getOrgId());
        sysOperateLogVo.setOrgName(orgName);

        return sysOperateLogVo;
    }

    @Override
    public PageResult<SysOperateLogVo> page(SysOperateLogPageDto sysOperateLogPageDto) {
        IPage<SysOperateLog> page = baseMapper.selectPage(sysOperateLogPageDto.toPage(), getWrapper(sysOperateLogPageDto));
        return new PageResult<>(page.getTotal(), SysOperateLogConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public void export(SysOperateLogPageDto sysOperateLogPageDto) {
        List<SysOperateLog> sysOperateLogList = baseMapper.selectList(getWrapper(sysOperateLogPageDto));
        List<SysOperateLogVo> sysOperateLogVoList = SysOperateLogConvert.INSTANCE.convert(sysOperateLogList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysOperateLogVo.class, "操作日志_".concat(today), "操作日志", sysOperateLogVoList);
    }

    @Override
    public void cleanOperateLog() {
        baseMapper.cleanOperateLog();
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
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getName()), SysOperateLog::getName, sysOperateLogPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getModuleName()), SysOperateLog::getModuleName, sysOperateLogPageDto.getModuleName());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getRequestUri()), SysOperateLog::getRequestUri, sysOperateLogPageDto.getRequestUri());
        wrapper.like(StringUtils.isNotEmpty(sysOperateLogPageDto.getUsername()), SysOperateLog::getUsername, sysOperateLogPageDto.getUsername());
        addTimeRangeCondition(wrapper, SysOperateLog::getOperatedTime, sysOperateLogPageDto.getOperatedTimeStart(), sysOperateLogPageDto.getOperatedTimeEnd());

        return wrapper;
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
                        .rightPop(CacheConstants.SYSTEM_OPERATE_LOG_KEY);
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
