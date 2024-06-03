package com.brycehan.cloud.auth.entity.convert;

import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.common.core.base.LoginUser;
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
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVo convert(LoginUser loginUser);

}