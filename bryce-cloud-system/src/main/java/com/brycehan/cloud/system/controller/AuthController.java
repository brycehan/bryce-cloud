package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.base.dto.AccountLoginDto;
import com.brycehan.cloud.common.base.dto.PhoneLoginDto;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.base.vo.LoginVo;
import com.brycehan.cloud.framework.security.TokenUtils;
import com.brycehan.cloud.framework.security.context.LoginUser;
import com.brycehan.cloud.framework.security.context.LoginUserContext;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.service.AuthService;
import com.brycehan.cloud.system.service.SysMenuService;
import com.brycehan.cloud.system.service.SysUserService;
import com.brycehan.cloud.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 认证API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "认证", description = "auth")
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    /**
     * 账号登录
     *
     * @param accountLoginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "账号登录")
    @PostMapping(path = "/loginByAccount")
    public ResponseResult<LoginVo> loginByAccount(@Validated @RequestBody AccountLoginDto accountLoginDto) {
        LoginVo loginVo = authService.loginByAccount(accountLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 手机验证码登录
     *
     * @param phoneLoginDto 手机验证码登录dto
     * @return 响应结果
     */
    @Operation(summary = "手机验证码登录")
    @PostMapping(path = "/loginByPhone")
    public ResponseResult<LoginVo> loginByPhone(@Validated @RequestBody PhoneLoginDto phoneLoginDto) {
        LoginVo loginVo = authService.loginByPhone(phoneLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 获取用户权限标识
     *
     * @return 响应结果
     */
    @Operation(summary = "获取用户权限标识", description = "用户权限标识集合")
    @GetMapping(path = "/authority")
    public ResponseResult<Set<String>> authority() {
        LoginUser loginUser = LoginUserContext.currentUser();
        Set<String> authoritySet = this.sysMenuService.findAuthority(loginUser);
        return ResponseResult.ok(authoritySet);
    }

    /**
     * 查询系统登录用户详情
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录用户详情")
    @GetMapping(path = "/currentUser")
    public ResponseResult<SysUser> currentUser() {
        LoginUser loginUser = LoginUserContext.currentUser();
        SysUser sysUser = this.sysUserService.getById(loginUser);
        sysUser.setPassword(null);
        // 角色权限
        Set<String> roleAuthoritySet = this.authService.getRoleAuthority(loginUser);
        // 菜单权限
        Set<String> menuAuthoritySet = this.authService.getMenuAuthority(loginUser);
        sysUser.setRoles(roleAuthoritySet);
        sysUser.setAuthoritySet(menuAuthoritySet);
        return ResponseResult.ok(sysUser);
    }

    /**
     * 获取路由信息
     *
     * @return 路由列表
     */
    @Operation(summary = "获取菜单列表")
    @GetMapping(path = "/nav")
    public ResponseResult<List<SysMenuVo>> nav() {
        List<SysMenuVo> list = this.sysMenuService.getMenuTreeList(LoginUserContext.currentUser(), "M");

        return ResponseResult.ok(list);
    }

    /**
     * 退出登录
     *
     * @return 响应结果
     */
    @Operation(summary = "退出登录")
    @GetMapping(path = "/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        this.authService.logout(TokenUtils.getAccessToken(request));
        return ResponseResult.ok();
    }

}
