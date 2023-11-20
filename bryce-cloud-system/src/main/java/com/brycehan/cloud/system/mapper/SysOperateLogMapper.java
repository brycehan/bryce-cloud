package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper接口
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Mapper
public interface SysOperateLogMapper extends BryceBaseMapper<SysOperateLog> {

}
