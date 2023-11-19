package com.brycehan.cloud.quartz.convert;

import com.brycehan.cloud.quartz.dto.QuartzJobLogDto;
import com.brycehan.cloud.quartz.entity.QuartzJobLog;
import com.brycehan.cloud.quartz.vo.QuartzJobLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * quartz 定时任务调度日志转换器
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuartzJobLogConvert {

    QuartzJobLogConvert INSTANCE = Mappers.getMapper(QuartzJobLogConvert.class);

    QuartzJobLog convert(QuartzJobLogDto quartzJobLogDto);

    QuartzJobLogVo convert(QuartzJobLog quartzJobLog);

    List<QuartzJobLogVo> convert(List<QuartzJobLog> quartzJobLogList);

}