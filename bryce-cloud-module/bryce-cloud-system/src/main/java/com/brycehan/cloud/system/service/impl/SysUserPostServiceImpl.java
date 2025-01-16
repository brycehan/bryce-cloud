package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.po.SysUserPost;
import com.brycehan.cloud.system.mapper.SysUserPostMapper;
import com.brycehan.cloud.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 系统用户岗位关系服务实现
 *
 * @since 2023/09/30
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysUserPostServiceImpl extends BaseServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long userId, List<Long> postIds) {
        // 数据库用户岗位IDs
        List<Long> dbPostIds = getPostIdsByUserId(userId);

        // 需要新增的岗位IDs
        Collection<Long> insertPostIds = CollUtil.subtract(postIds, dbPostIds);
        if (CollUtil.isNotEmpty(insertPostIds)) {
            List<SysUserPost> list = insertPostIds.stream().map(postId -> {
                SysUserPost userPost = new SysUserPost();
                userPost.setId(IdGenerator.nextId());
                userPost.setUserId(userId);
                userPost.setPostId(postId);
                return userPost;
            }).toList();

            // 批量新增
            saveBatch(list);
        }

        // 需要删除的岗位IDs
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        if (CollUtil.isNotEmpty(deletePostIds)) {
            LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserPost::getUserId, userId);
            queryWrapper.in(SysUserPost::getPostId, deletePostIds);

            this.remove(queryWrapper);
        }
    }

    /**
     * 根据用户ID查询拥有的岗位IDs
     *
     * @param userId 用户ID
     * @return 岗位IDs
     */
    @Override
    public List<Long> getPostIdsByUserId(Long userId) {
        LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserPost::getUserId, userId);

        List<SysUserPost> sysUserPosts = baseMapper.selectList(queryWrapper);

        return sysUserPosts.stream().map(SysUserPost::getPostId)
                .toList();
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getUserId, userIds));
    }

    @Override
    public void deleteByPostIds(List<Long> postIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getPostId, postIds));
    }

}
