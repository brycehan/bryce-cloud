package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.framework.security.context.LoginUser;
import com.brycehan.cloud.system.dto.SysUserDto;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统用户转换器
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser convert(SysUserDto sysUserDto);

    SysUserVo convert(SysUser sysUser);

    List<SysUserVo> convert(List<SysUser> sysUserList);

    LoginUser convertLoginUser(SysUser sysUser);

}