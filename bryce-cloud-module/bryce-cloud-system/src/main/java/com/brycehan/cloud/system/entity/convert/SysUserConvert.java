package com.brycehan.cloud.system.entity.convert;

import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.dto.ProfileDto;
import com.brycehan.cloud.system.entity.dto.SysUserDto;
import com.brycehan.cloud.system.entity.dto.SysUserExcelDto;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统用户转换器
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser convert(SysUserDto sysUserDto);

    SysUser convert(ProfileDto profileDto);

    SysUserVo convert(SysUser sysUser);

    SysUserVo convert(LoginUser loginUser);

    List<SysUserVo> convert(List<SysUser> sysUserList);

    LoginUser convertLoginUser(SysUser sysUser);

    List<SysUser> convertList(List<SysUserExcelDto> list);

}