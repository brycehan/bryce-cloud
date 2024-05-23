package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色关系Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysUserRoleMapper extends BryceBaseMapper<SysUserRole> {

}
