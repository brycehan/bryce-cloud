package com.brycehan.cloud.system.entity.convert;

import com.brycehan.cloud.common.operatelog.aspect.OperateLogDto;
import com.brycehan.cloud.system.entity.dto.SysOperateLogDto;
import com.brycehan.cloud.system.entity.po.SysOperateLog;
import com.brycehan.cloud.system.entity.vo.SysOperateLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统操作日志转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOperateLogConvert {

    SysOperateLogConvert INSTANCE = Mappers.getMapper(SysOperateLogConvert.class);

    SysOperateLog convert(SysOperateLogDto sysOperateLogDto);

    SysOperateLogVo convert(SysOperateLog sysOperateLog);

    SysOperateLog convert(OperateLogDto operateLogDto);

    List<SysOperateLogVo> convert(List<SysOperateLog> sysOperateLogList);

}
