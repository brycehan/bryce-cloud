package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysRoleDto;
import com.brycehan.cloud.system.entity.SysRole;
import com.brycehan.cloud.system.vo.SysRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统角色转换器
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRole convert(SysRoleDto sysRoleDto);

    SysRoleVo convert(SysRole sysRole);

    List<SysRoleVo> convert(List<SysRole> sysRoleList);

}