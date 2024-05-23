package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.cloud.common.core.base.id.IdGenerator;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.entity.po.SysUserRole;
import com.brycehan.cloud.system.mapper.SysUserRoleMapper;
import com.brycehan.cloud.system.service.SysUserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 系统用户角色关系服务实现
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIds) {
        // 数据库用户角色IDs
        List<Long> dbRoleIds = getRoleIdsByUserId(userId);

        // 需要新增的角色IDs
        Collection<Long> insertRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        if (CollUtil.isNotEmpty(insertRoleIds)) {
            List<SysUserRole> list = insertRoleIds.stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setId(IdGenerator.nextId());
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                return userRole;
            }).toList();

            // 批量新增
            this.saveBatch(list);
        }

        // 需要删除的角色IDs
        Collection<Long> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIds);
        if (CollUtil.isNotEmpty(deleteRoleIds)) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, userId);
            queryWrapper.in(SysUserRole::getRoleId, deleteRoleIds);

            this.remove(queryWrapper);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUsers(Long roleId, List<Long> userIds) {
        // 过滤无效参数
        List<Long> ids = userIds.stream()
                .filter(Objects::nonNull)
                .toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 过滤已经添加的数据
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        queryWrapper.in(SysUserRole::getUserId, userIds);
        List<SysUserRole> dbUserRoles = this.baseMapper.selectList(queryWrapper);

        List<SysUserRole> userRoleList = userIds.stream()
                .filter(userId -> dbUserRoles.stream().noneMatch(sysUserRole -> sysUserRole.getUserId().equals(userId)))
                .map(userId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setId(IdGenerator.nextId());
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                }).toList();

        if (CollectionUtils.isNotEmpty(userRoleList)) {
            // 批量新增
            saveBatch(userRoleList);
        }
    }

    /**
     * 根据用户ID查询拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色IDs
     */
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);

        List<SysUserRole> sysUserRoles = this.baseMapper.selectList(queryWrapper);

        return sysUserRoles.stream().map(SysUserRole::getRoleId)
                .toList();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getRoleId, roleIds));
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, userIds));
    }

    @Override
    public void deleteByRoleIdAndUserIds(Long roleId, List<Long> userIds) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        queryWrapper.in(SysUserRole::getUserId, userIds);

        remove(queryWrapper);
    }

}
