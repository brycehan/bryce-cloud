package com.brycehan.cloud.quartz.mapper;

import com.brycehan.cloud.common.base.mapper.BryceBaseMapper;
import com.brycehan.cloud.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
* quartz 定时任务调度Mapper接口
*
* @author Bryce Han
* @since 2023/10/17
*/
@Mapper
public interface QuartzJobMapper extends BryceBaseMapper<QuartzJob> {

}
