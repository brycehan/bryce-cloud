package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.entity.dto.SysUserDto;
import com.brycehan.cloud.api.system.entity.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.system.entity.dto.SysUsernameDto;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.service.SysUserDetailsService;
import com.brycehan.cloud.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户Api
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统用户Api")
@RequestMapping(path = SysUserApi.PATH)
@RestController
@RequiredArgsConstructor
public class SysUserApiController implements SysUserApi {

    private final SysUserService sysUserService;
    private final SysUserDetailsService sysUserDetailsService;

    /**
     * 查询系统账号
     *
     * @param username 系统账号
     *
     * @return paramKey 是否存在
     */
    @Operation(summary = "查询系统账号")
    @PreAuthorize("@auth.hasInnerCall()")
    @Override
    public ResponseResult<LoginUser> loadUserByUsername(String username) {
        // 查询用户
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            log.debug("loadUserByUsername, 登录用户：{}不存在.", username);
            return ResponseResult.ok();
        }

        // 创建用户详情
        UserDetails userDetails = this.sysUserDetailsService.getUserDetails(sysUser);
        return ResponseResult.ok((LoginUser) userDetails);
    }

    /**
     * 查询系统账号
     *
     * @param phone 手机号
     *
     * @return paramKey 是否存在
     */
    @Operation(summary = "查询系统账号")
    @PreAuthorize("@auth.hasInnerCall()")
    @Override
    public ResponseResult<LoginUser> loadUserByPhone(String phone) {
        // 查询用户
        SysUser sysUser = this.sysUserService.getByPhone(phone);

        if (sysUser == null) {
            log.debug("loadUserByPhone, 登录用户：{}不存在.", phone);
            return ResponseResult.ok();
        }

        // 创建用户详情
        UserDetails userDetails = this.sysUserDetailsService.getUserDetails(sysUser);
        return ResponseResult.ok((LoginUser) userDetails);
    }

    /**
     * 获取登录对象
     *
     * @param id 用户ID
     * @return 登录对象
     */
    @Override
    @Operation(summary = "获取登录对象")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<LoginUser> loadUserById(Long id) {
        // 查询用户
        SysUser sysUser = this.sysUserService.getById(id);

        if (sysUser == null) {
            log.debug("登录用户ID：{}不存在.", id);
            return ResponseResult.error("手机号或验证错误");
        }

        // 创建用户详情
        UserDetails userDetails = this.sysUserDetailsService.getUserDetails(sysUser);
        return ResponseResult.ok((LoginUser) userDetails);
    }

    /**
     * 注册用户
     *
     * @param sysUserDto 系统用户
     * @return 注册结果
     */
    @Override
    @Operation(summary = "注册用户")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<SysUserVo> registerUser(SysUserDto sysUserDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDto, sysUser);

        sysUser = this.sysUserService.registerUser(sysUser);

        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);

        return ResponseResult.ok(sysUserVo);
    }

    /**
     * 更新用户登录信息
     *
     * @param sysUserLoginInfoDto 系统用户登录信息
     * @return 更新状态
     */
    @Override
    @Operation(summary = "更新用户登录信息")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<Boolean> updateLoginInfo(SysUserLoginInfoDto sysUserLoginInfoDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserLoginInfoDto, sysUser);
        sysUser.setUpdatedUserId(sysUserLoginInfoDto.getId());
        boolean updated = this.sysUserService.updateById(sysUser);
        return ResponseResult.ok(updated);
    }

    /**
     * 校验用户账号是否唯一
     *
     * @param username 用户账号
     * @return 是否唯一
     */
    @Override
    @Operation(summary = "校验用户账号是否唯一")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<Boolean> checkUsernameUnique(String username) {
        SysUsernameDto sysUsernameDto = new SysUsernameDto();
        sysUsernameDto.setUsername(username);
        boolean checked = this.sysUserService.checkUsernameUnique(sysUsernameDto);
        return ResponseResult.ok(checked);
    }
}
