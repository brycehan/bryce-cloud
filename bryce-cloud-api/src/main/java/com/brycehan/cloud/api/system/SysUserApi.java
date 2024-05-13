package com.brycehan.cloud.api.system;

import com.brycehan.cloud.api.ServerNames;
import com.brycehan.cloud.api.system.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.common.base.LoginUser;
import com.brycehan.cloud.common.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sysUser")
public interface SysUserApi {

    /**
     * 查询系统账号
     *
     * @param username 系统账号
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/api/user/{username}")
    ResponseResult<LoginUser> loadUserByUsername(@PathVariable String username);

    /**
     * 查询系统账号
     *
     * @param phone 手机号
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/api/user/phone/{phone}")
    ResponseResult<LoginUser> loadUserByPhone(@PathVariable String phone);

    /**
     * 更新登录信息
     * @param sysUserLoginInfoDto 系统用户登录信息
     * @return 更新状态
     */
    @PostMapping(path = "/api/user/updateLoginInfo")
    ResponseResult<Boolean> updateLoginInfo(@RequestBody SysUserLoginInfoDto sysUserLoginInfoDto);

}
