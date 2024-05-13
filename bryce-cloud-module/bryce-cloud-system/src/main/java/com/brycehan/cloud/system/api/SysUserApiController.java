package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.SysUserApi;
import com.brycehan.cloud.api.system.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.common.base.LoginUser;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.system.convert.SysUserConvert;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.mapper.SysUserMapper;
import com.brycehan.cloud.system.service.SysUserDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统用户Api实现")
@RestController
@RequiredArgsConstructor
public class SysUserApiController implements SysUserApi {

    private final SysUserMapper sysUserMapper;

    private final SysUserDetailsService sysUserDetailsService;

    @Override
    public ResponseResult<LoginUser> loadUserByUsername(String username) {
        // 查询用户
        SysUser sysUser = sysUserMapper.getByUsername(username);

        if (sysUser == null) {
            log.debug("loadUserByUsername, 登录用户：{}不存在.", username);
            return ResponseResult.error("用户名或密码错误");
        }

        // 创建用户详情
        UserDetails userDetails = this.sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertLoginUser(sysUser));
        return ResponseResult.ok((LoginUser) userDetails);
    }

    @Override
    public ResponseResult<LoginUser> loadUserByPhone(String phone) {
        // 查询用户
        SysUser sysUser = sysUserMapper.getByPhone(phone);

        if (sysUser == null) {
            log.debug("loadUserByPhone, 登录用户：{}不存在.", phone);
            return ResponseResult.error("手机号或验证错误");
        }

        // 创建用户详情
        UserDetails userDetails = this.sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertLoginUser(sysUser));
        return ResponseResult.ok((LoginUser) userDetails);
    }

    @Override
    public ResponseResult<Boolean> updateLoginInfo(SysUserLoginInfoDto sysUserLoginInfoDto) {
        SysUser sysUser = new SysUser();
        sysUser.setId(sysUser.getId());
        sysUser.setLastLoginIp(sysUserLoginInfoDto.getLastLoginIp());
        sysUser.setLastLoginTime(sysUserLoginInfoDto.getLastLoginTime());

        int updated = this.sysUserMapper.updateById(sysUser);
        return ResponseResult.ok(updated == 1);
    }

}
