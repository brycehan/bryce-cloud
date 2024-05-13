package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysDictDataDto;
import com.brycehan.cloud.system.entity.SysDictData;
import com.brycehan.cloud.system.vo.SysDictDataVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典数据转换器
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictData convert(SysDictDataDto sysDictDataDto);

    SysDictDataVo convert(SysDictData sysDictData);

    List<SysDictDataVo> convert(List<SysDictData> sysDictDataList);

}