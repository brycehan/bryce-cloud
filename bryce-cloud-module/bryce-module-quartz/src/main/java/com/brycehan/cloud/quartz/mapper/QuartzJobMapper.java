package com.brycehan.cloud.quartz.mapper;

import com.brycehan.cloud.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
* quartz 定时任务调度 Mapper 接口
*
* @author Bryce Han
* @since 2023/10/17
*/
@Mapper
public interface QuartzJobMapper extends BryceBaseMapper<QuartzJob> {

}
