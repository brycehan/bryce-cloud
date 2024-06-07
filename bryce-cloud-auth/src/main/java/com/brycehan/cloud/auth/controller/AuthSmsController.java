package com.brycehan.cloud.auth.controller;

import com.brycehan.cloud.common.core.enums.SmsType;
import com.brycehan.cloud.auth.entity.vo.SmsCodeVo;
import com.brycehan.cloud.auth.service.AuthSmsService;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证短信API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "登录认证短信API")
@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class AuthSmsController {

    private final AuthSmsService authSmsService;

    /**
     * 生成登录验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成登录验证码")
    @GetMapping(path = "/login/code")
    public ResponseResult<?> sendLoginCode(String phone) {
        SmsCodeVo smsCodeVo = this.authSmsService.sendCode(phone, SmsType.LOGIN);
        return ResponseResult.ok(smsCodeVo);

    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/register/code")
    public ResponseResult<?> sendRegisterCode(String phone) {
        SmsCodeVo smsCodeVo = this.authSmsService.sendCode(phone, SmsType.REGISTER);
        return ResponseResult.ok(smsCodeVo);
    }

    /**
     * 是否开启短信功能
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启短信功能")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        boolean smsEnabled = this.authSmsService.smsEnabled();
        return ResponseResult.ok(smsEnabled);
    }

}
