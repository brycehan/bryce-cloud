package com.brycehan.cloud.monitor.controller;

import cn.hutool.core.collection.ListUtil;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import com.brycehan.cloud.monitor.entity.dto.OnlineUserPageDto;
import com.brycehan.cloud.monitor.entity.vo.OnlineUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 在线用户API
 *
 * @since 2023/10/12
 * @author Bryce Han
 */
@Tag(name = "在线用户")
@RestController
@RequestMapping(path = "/onlineUser")
@RequiredArgsConstructor
public class OnlineUserController {

    private final RedisTemplate<String, LoginUser> redisTemplate;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 在线用户分页查询
     *
     * @param onlineUserPageDto 查询条件
     * @return 在线用户分页列表
     */
    @Operation(summary = "在线用户分页查询")
    @PreAuthorize("hasAuthority('monitor:onlineUser:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<OnlineUserVo>> page(@Validated @RequestBody OnlineUserPageDto onlineUserPageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY.concat("*");
        Set<String> keys = this.redisTemplate.keys(patternKeys);

        // 逻辑分页
        List<String> keyList = ListUtil.page(onlineUserPageDto.getCurrent() - 1, onlineUserPageDto.getSize(), ListUtil.toList(keys));

        // 分页数据
        List<OnlineUserVo> list = new ArrayList<>();
        keyList.forEach(key -> {
            LoginUser loginUser = this.redisTemplate.opsForValue()
                    .get(key);
            if(loginUser != null) {
                OnlineUserVo onlineUserVo = new OnlineUserVo();
                BeanUtils.copyProperties(loginUser, onlineUserVo);
                list.add(onlineUserVo);
            }
        });

        assert keys != null;
        return ResponseResult.ok(new PageResult<>(keys.size(), list));
    }

    /**
     * 强制退出
     *
     * @param tokenKey 会话存储Key
     * @return 响应结果
     */
    @Operation(summary = "强制退出")
    @OperateLog(type = OperateType.FORCE_QUIT)
    @PreAuthorize("hasAuthority('monitor:onlineUser:delete')")
    @DeleteMapping(path = "/{tokenKey}")
    public ResponseResult<Void> delete(@PathVariable String tokenKey) {

        if(StringUtils.isNotBlank(tokenKey)) {
            // 删除用户信息
            this.jwtTokenProvider.deleteLoginUser(tokenKey);
        }

        return ResponseResult.ok();
    }

}
