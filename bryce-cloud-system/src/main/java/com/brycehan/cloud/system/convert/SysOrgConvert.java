package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysOrgDto;
import com.brycehan.cloud.system.entity.SysOrg;
import com.brycehan.cloud.system.vo.SysOrgVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统机构转换器
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper
public interface SysOrgConvert {

    SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

    SysOrg convert(SysOrgDto sysOrgDto);

    SysOrgVo convert(SysOrg sysOrg);

    List<SysOrgVo> convert(List<SysOrg> sysOrgList);

}