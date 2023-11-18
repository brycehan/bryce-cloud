package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.framework.operatelog.OperateLogDto;
import com.brycehan.cloud.system.dto.SysOperateLogDto;
import com.brycehan.cloud.system.entity.SysOperateLog;
import com.brycehan.cloud.system.vo.SysOperateLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统操作日志转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper
public interface SysOperateLogConvert {

    SysOperateLogConvert INSTANCE = Mappers.getMapper(SysOperateLogConvert.class);

    SysOperateLog convert(SysOperateLogDto sysOperateLogDto);

    SysOperateLogVo convert(SysOperateLog sysOperateLog);

    SysOperateLog convert(OperateLogDto operateLogDto);

    List<SysOperateLogVo> convert(List<SysOperateLog> sysOperateLogList);

}
