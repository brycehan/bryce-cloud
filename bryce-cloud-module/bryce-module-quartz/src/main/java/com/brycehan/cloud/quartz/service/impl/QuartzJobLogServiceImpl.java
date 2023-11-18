package com.brycehan.cloud.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.util.DateTimeUtils;
import com.brycehan.cloud.common.util.ExcelUtils;
import com.brycehan.cloud.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.quartz.convert.QuartzJobLogConvert;
import com.brycehan.cloud.quartz.dto.QuartzJobLogPageDto;
import com.brycehan.cloud.quartz.entity.QuartzJobLog;
import com.brycehan.cloud.quartz.mapper.QuartzJobLogMapper;
import com.brycehan.cloud.quartz.service.QuartzJobLogService;
import com.brycehan.cloud.quartz.vo.QuartzJobLogVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * quartz 定时任务调度日志服务实现
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class QuartzJobLogServiceImpl extends BaseServiceImpl<QuartzJobLogMapper, QuartzJobLog> implements QuartzJobLogService {

    @Override
    public PageResult<QuartzJobLogVo> page(QuartzJobLogPageDto quartzJobLogPageDto) {

        IPage<QuartzJobLog> page = this.baseMapper.selectPage(getPage(quartzJobLogPageDto), getWrapper(quartzJobLogPageDto));

        return new PageResult<>(page.getTotal(), QuartzJobLogConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param quartzJobLogPageDto quartz 定时任务调度日志分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<QuartzJobLog> getWrapper(QuartzJobLogPageDto quartzJobLogPageDto){
        LambdaQueryWrapper<QuartzJobLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(quartzJobLogPageDto.getJobName()), QuartzJobLog::getJobName, quartzJobLogPageDto.getJobName());
        wrapper.eq(StringUtils.isNotEmpty(quartzJobLogPageDto.getJobGroup()), QuartzJobLog::getJobGroup, quartzJobLogPageDto.getJobGroup());
        return wrapper;
    }

    @Override
    public void export(QuartzJobLogPageDto quartzJobLogPageDto) {
        List<QuartzJobLog> quartzJobLogList = this.baseMapper.selectList(getWrapper(quartzJobLogPageDto));
        List<QuartzJobLogVo> quartzJobLogVoList = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogList);
        ExcelUtils.export(QuartzJobLogVo.class, "quartz 定时任务调度日志_".concat(DateTimeUtils.today()), "quartz 定时任务调度日志", quartzJobLogVoList);
    }

}
