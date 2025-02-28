package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.dto.SysUserDto;
import com.brycehan.cloud.api.system.entity.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统用户Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SysUserApi {

    String PATH = "/api/sysUser";

    /**
     * 查询系统账号
     *
     * @param username 系统账号
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/username/{username}")
    ResponseResult<LoginUser> loadUserByUsername(@PathVariable String username);

    /**
     * 查询系统账号
     *
     * @param phone 手机号
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/phone/{phone}")
    ResponseResult<LoginUser> loadUserByPhone(@PathVariable String phone);

    /**
     * 获取登录对象
     *
     * @param id 用户ID
     * @return 登录对象
     */
    @GetMapping(path = "/id/{id}")
    ResponseResult<LoginUser> loadUserById(@PathVariable Long id);

    /**
     * 注册用户
     *
     * @param sysUserDto 系统用户
     * @return 注册结果
     */
    @PostMapping(path = "/register")
    ResponseResult<SysUserVo> registerUser(@RequestBody SysUserDto sysUserDto);

    /**
     * 更新用户登录信息
     *
     * @param sysUserLoginInfoDto 系统用户登录信息
     * @return 更新状态
     */
    @PostMapping(path = "/updateLoginInfo")
    ResponseResult<Boolean> updateLoginInfo(@RequestBody SysUserLoginInfoDto sysUserLoginInfoDto);

    /**
     * 校验用户账号是否唯一
     *
     * @param username 用户账号
     * @return 是否唯一
     */
    @GetMapping(path = "/checkUsernameUnique/{username}")
    ResponseResult<Boolean> checkUsernameUnique(@PathVariable String username);

}
