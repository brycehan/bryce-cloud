package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 系统角色Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleMapper extends BryceBaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色编码
     *
     * @param userId 用户ID
     * @return 角色编码集合
     */
    Set<SysRole> getRoleByUserId(Long userId);

}
