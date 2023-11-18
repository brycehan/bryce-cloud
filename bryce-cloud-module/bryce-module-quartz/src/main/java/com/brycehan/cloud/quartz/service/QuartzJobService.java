package com.brycehan.cloud.quartz.service;

import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.framework.mybatis.service.BaseService;
import com.brycehan.cloud.quartz.convert.QuartzJobConvert;
import com.brycehan.cloud.quartz.dto.QuartzJobDto;
import com.brycehan.cloud.quartz.dto.QuartzJobPageDto;
import com.brycehan.cloud.quartz.entity.QuartzJob;
import com.brycehan.cloud.quartz.vo.QuartzJobVo;

/**
 * quartz 定时任务调度服务
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
public interface QuartzJobService extends BaseService<QuartzJob> {

    /**
     * 添加quartz 定时任务调度
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     */
    default void save(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);
        quartzJob.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(quartzJob);
    }

    /**
     * 更新quartz 定时任务调度
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     */
    default void update(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);
        this.getBaseMapper().updateById(quartzJob);
    }

    /**
     * quartz 定时任务调度分页查询
     *
     * @param quartzJobPageDto 查询条件
     * @return 分页信息
     */
    PageResult<QuartzJobVo> page(QuartzJobPageDto quartzJobPageDto);

    /**
     * quartz 定时任务调度导出数据
     *
     * @param quartzJobPageDto quartz 定时任务调度查询条件
     */
    void export(QuartzJobPageDto quartzJobPageDto);

    /**
     * quartz 定时任务立即执行
     *
     * @param quartzJobDto quartz 定时任务
     */
    void run(QuartzJobDto quartzJobDto);

    /**
     * 修改 quartz 定时任务状态
     * @param quartzJobDto quartz 定时任务调度Dto
     */
    void changeStatus(QuartzJobDto quartzJobDto);

}
