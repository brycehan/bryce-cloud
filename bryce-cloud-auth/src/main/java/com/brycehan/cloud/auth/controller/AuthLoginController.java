package com.brycehan.cloud.auth.controller;

import com.brycehan.cloud.api.system.vo.SysUserVo;
import com.brycehan.cloud.auth.entity.convert.SysUserConvert;
import com.brycehan.cloud.auth.service.AuthLoginService;
import com.brycehan.cloud.common.core.base.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.base.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.base.vo.LoginVo;
import com.brycehan.cloud.common.security.context.LoginUserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "登录认证")
@RestController
@RequiredArgsConstructor
public class AuthLoginController {

    private final AuthLoginService authLoginService;

    /**
     * 账号登录
     *
     * @param accountLoginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "账号密码登录")
    @PostMapping(path = "/loginByAccount")
    public ResponseResult<LoginVo> loginByAccount(@Validated @RequestBody AccountLoginDto accountLoginDto) {
        LoginVo loginVo = authLoginService.loginByAccount(accountLoginDto);
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
        LoginVo loginVo = authLoginService.loginByPhone(phoneLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 查询系统登录用户详情
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录用户详情")
    @GetMapping(path = "/currentUser")
    public ResponseResult<SysUserVo> currentUser() {
        SysUserVo sysUserVo = SysUserConvert.INSTANCE.convert(LoginUserContext.currentUser());
        return ResponseResult.ok(sysUserVo);
    }

    /**
     * 退出登录
     *
     * @return 响应结果
     */
    @Operation(summary = "退出登录")
    @GetMapping(path = "/logout")
    public ResponseResult<Void> logout() {
        this.authLoginService.logout(LoginUserContext.currentUser());
        return ResponseResult.ok();
    }

}
