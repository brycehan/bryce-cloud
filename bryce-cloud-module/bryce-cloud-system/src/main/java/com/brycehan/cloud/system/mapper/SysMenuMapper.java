package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.common.MenuType;
import com.brycehan.cloud.system.entity.po.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单 Mapper 接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysMenuMapper extends BryceBaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    Set<String> findAuthorityByUserId(Long userId);

    /**
     * 根据角色ID查询菜单权限
     *
     * @param roleId 角色ID
     * @return 菜单权限集合
     */
    Set<String> findAuthorityByRoleId(Long roleId);

    /**
     * 查询菜单树列表
     *
     * @param userId 用户ID
     * @param type   菜单类型
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeList(Long userId, MenuType... type);
}
