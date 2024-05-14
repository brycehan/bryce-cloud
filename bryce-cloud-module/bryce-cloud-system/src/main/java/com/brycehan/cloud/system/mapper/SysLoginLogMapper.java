package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.BryceBaseMapper;
import com.brycehan.cloud.system.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统登录日志Mapper接口
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Mapper
public interface SysLoginLogMapper extends BryceBaseMapper<SysLoginLog> {

}
