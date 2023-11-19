package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysTenantDto;
import com.brycehan.cloud.system.entity.SysTenant;
import com.brycehan.cloud.system.vo.SysTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统租户转换器
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysTenantConvert {

    SysTenantConvert INSTANCE = Mappers.getMapper(SysTenantConvert.class);

    SysTenant convert(SysTenantDto sysTenantDto);

    SysTenantVo convert(SysTenant sysTenant);

    List<SysTenantVo> convert(List<SysTenant> sysTenantList);

}