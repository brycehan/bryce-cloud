package com.brycehan.cloud.system.entity.convert;

import com.brycehan.cloud.system.entity.dto.SysDictTypeDto;
import com.brycehan.cloud.system.entity.po.SysDictType;
import com.brycehan.cloud.system.entity.vo.SysDictTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典类型转换器
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictTypeConvert {

    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictType convert(SysDictTypeDto sysDictTypeDto);

    SysDictTypeVo convert(SysDictType sysDictType);

    List<SysDictTypeVo> convert(List<SysDictType> sysDictTypeList);

}