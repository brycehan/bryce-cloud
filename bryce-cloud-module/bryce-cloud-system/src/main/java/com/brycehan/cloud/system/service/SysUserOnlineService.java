package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.system.entity.dto.SysUserOnlinePageDto;
import com.brycehan.cloud.system.entity.vo.SysUserOnlineVo;

/**
 * 在线用户服务
 *
 * @author Bryce Han
 * @since 2024/12/2
 */
public interface SysUserOnlineService {

    /**
     * 带过滤条件的在线用户分页查询
     *
     * @param sysUserOnlinePageDto 在线用户分页查询参数
     * @return 在线用户分页查询结果
     */
    PageResult<SysUserOnlineVo> pageByUsernameAndLoginIp(SysUserOnlinePageDto sysUserOnlinePageDto);

    /**
     * 在线用户分页查询
     *
     * @param sysUserOnlinePageDto 在线用户分页查询参数
     * @return 在线用户分页查询结果
     */
    PageResult<SysUserOnlineVo> page(SysUserOnlinePageDto sysUserOnlinePageDto);

    /**
     * 强制退出
     *
     * @param userKey 用户Key
     */
    void deleteLoginUser(String userKey);

}
