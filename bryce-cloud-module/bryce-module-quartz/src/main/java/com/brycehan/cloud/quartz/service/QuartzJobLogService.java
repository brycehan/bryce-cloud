package com.brycehan.cloud.quartz.service;

import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.framework.mybatis.service.BaseService;
import com.brycehan.cloud.quartz.convert.QuartzJobLogConvert;
import com.brycehan.cloud.quartz.dto.QuartzJobLogDto;
import com.brycehan.cloud.quartz.dto.QuartzJobLogPageDto;
import com.brycehan.cloud.quartz.entity.QuartzJobLog;
import com.brycehan.cloud.quartz.vo.QuartzJobLogVo;

/**
 * quartz 定时任务调度日志服务
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
public interface QuartzJobLogService extends BaseService<QuartzJobLog> {

    /**
     * 添加quartz 定时任务调度日志
     *
     * @param quartzJobLogDto quartz 定时任务调度日志Dto
     */
    default void save(QuartzJobLogDto quartzJobLogDto) {
        QuartzJobLog quartzJobLog = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogDto);
        quartzJobLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(quartzJobLog);
    }

    /**
     * 更新quartz 定时任务调度日志
     *
     * @param quartzJobLogDto quartz 定时任务调度日志Dto
     */
    default void update(QuartzJobLogDto quartzJobLogDto) {
        QuartzJobLog quartzJobLog = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogDto);
        this.getBaseMapper().updateById(quartzJobLog);
    }

    /**
     * quartz 定时任务调度日志分页查询
     *
     * @param quartzJobLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<QuartzJobLogVo> page(QuartzJobLogPageDto quartzJobLogPageDto);

    /**
     * quartz 定时任务调度日志导出数据
     *
     * @param quartzJobLogPageDto quartz 定时任务调度日志查询条件
     */
    void export(QuartzJobLogPageDto quartzJobLogPageDto);

}
