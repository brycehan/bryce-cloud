package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import com.brycehan.cloud.system.entity.convert.SysUserOnlineConvert;
import com.brycehan.cloud.system.entity.dto.SysUserOnlinePageDto;
import com.brycehan.cloud.system.entity.vo.SysUserOnlineVo;
import com.brycehan.cloud.system.service.SysDeptService;
import com.brycehan.cloud.system.service.SysUserOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 在线用户服务实现类
 *
 * @author Bryce Han
 * @since 2024/12/2
 */
@Service
@RequiredArgsConstructor
public class SysUserOnlineServiceImpl implements SysUserOnlineService {

    private final RedisTemplate<String, LoginUser> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final SysDeptService sysDeptService;

    @Override
    public PageResult<SysUserOnlineVo> pageByUsernameAndLoginIp(SysUserOnlinePageDto onlineUserPageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY + "*";
        Set<String> keys = redisTemplate.keys(patternKeys);

        if (keys == null) {
            return PageResult.empty();
        }

        List<SysUserOnlineVo> list = new ArrayList<>();

        // 处理搜索过滤条件
        for (String key : keys) {
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            String username = onlineUserPageDto.getUsername();
            String loginIp = onlineUserPageDto.getLoginIp();
            if (StringUtils.hasText(username) && StringUtils.hasText(loginIp)) {
                list.add(getOnlineUserVo(loginUser, username, loginIp));
            } else if (StringUtils.hasText(username)) {
                list.add(getOnlineUserVoByUsername(loginUser, username));
            } else if (StringUtils.hasText(loginIp)) {
                list.add(getOnlineUserVoByLoginIp(loginUser, loginIp));
            }
        }

        list.removeAll(Collections.singleton(null));
        list.sort(Comparator.comparing(SysUserOnlineVo::getLoginTime).reversed());

        // 逻辑分页
        List<SysUserOnlineVo> sysUserOnlineVoList = ListUtil.page(onlineUserPageDto.getCurrent() - 1, onlineUserPageDto.getSize(), list);

        // 分页数据，处理部门名称
        Map<Long, String> deptNames = sysDeptService.getDeptNamesByIds(sysUserOnlineVoList.stream().map(SysUserOnlineVo::getDeptId).toList());
        sysUserOnlineVoList.forEach(sysUserOnlineVo -> sysUserOnlineVo.setDeptName(deptNames.get(sysUserOnlineVo.getDeptId())));

        return PageResult.of(sysUserOnlineVoList, sysUserOnlineVoList.size());
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param username  用户名
     * @param loginIp   登录IP
     * @return 在线用户信息
     */
    private SysUserOnlineVo getOnlineUserVo(LoginUser loginUser, String username, String loginIp) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(username, loginUser.getUsername()) && StrUtil.equals(loginIp, loginUser.getLoginIp())) {
            return SysUserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param username  用户名
     * @return 在线用户信息
     */
    private SysUserOnlineVo getOnlineUserVoByUsername(LoginUser loginUser, String username) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(username, loginUser.getUsername())) {
            return SysUserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param loginIp   登录IP
     * @return 在线用户信息
     */
    private SysUserOnlineVo getOnlineUserVoByLoginIp(LoginUser loginUser, String loginIp) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(loginIp, loginUser.getLoginIp())) {
            return SysUserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    @Override
    public PageResult<SysUserOnlineVo> page(SysUserOnlinePageDto onlineUserPageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY + "*";
        Set<String> keys = redisTemplate.keys(patternKeys);

        if (keys == null) {
            return PageResult.empty();
        }

        // 逻辑分页
        List<String> keyList = ListUtil.page(onlineUserPageDto.getCurrent() - 1, onlineUserPageDto.getSize(), ListUtil.toList(keys));

        // 分页数据
        List<SysUserOnlineVo> list = new ArrayList<>();
        keyList.forEach(key -> {
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            if(loginUser != null) {
                list.add(SysUserOnlineConvert.INSTANCE.convert(loginUser));
            }
        });
        list.sort(Comparator.comparing(SysUserOnlineVo::getLoginTime).reversed());

        // 处理部门名称
        Map<Long, String> deptNames = sysDeptService.getDeptNamesByIds(list.stream().map(SysUserOnlineVo::getDeptId).toList());
        list.forEach(sysUserOnlineVo -> sysUserOnlineVo.setDeptName(deptNames.get(sysUserOnlineVo.getDeptId())));

        return PageResult.of(list, keys.size());
    }

    @Override
    public void deleteLoginUser(String userKey) {
        if(StringUtils.hasText(userKey)) {
            // 删除用户信息
            jwtTokenProvider.deleteLoginUser(userKey);
        }
    }

}
