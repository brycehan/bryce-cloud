package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.framework.mybatis.BryceBaseMapper;
import com.brycehan.cloud.system.entity.SysUserRole;
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
