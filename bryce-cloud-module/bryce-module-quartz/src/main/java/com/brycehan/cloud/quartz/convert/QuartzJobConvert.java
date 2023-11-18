package com.brycehan.cloud.quartz.convert;

import com.brycehan.cloud.quartz.dto.QuartzJobDto;
import com.brycehan.cloud.quartz.entity.QuartzJob;
import com.brycehan.cloud.quartz.vo.QuartzJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * quartz 定时任务调度转换器
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Mapper
public interface QuartzJobConvert {

    QuartzJobConvert INSTANCE = Mappers.getMapper(QuartzJobConvert.class);

    QuartzJob convert(QuartzJobDto quartzJobDto);

    QuartzJobVo convert(QuartzJob quartzJob);

    List<QuartzJobVo> convert(List<QuartzJob> quartzJobList);

}