package com.brycehan.cloud.system.entity.convert;

import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.system.entity.vo.SysUserOnlineVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户转换器
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserOnlineConvert {

    SysUserOnlineConvert INSTANCE = Mappers.getMapper(SysUserOnlineConvert.class);

    SysUserOnlineVo convert(LoginUser loginUser);

}