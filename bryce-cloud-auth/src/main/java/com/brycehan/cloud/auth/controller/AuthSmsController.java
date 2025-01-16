package com.brycehan.cloud.auth.controller;

import com.brycehan.cloud.auth.service.AuthSmsService;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.SmsType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码Api
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "短信验证码")
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
        authSmsService.sendCode(phone, SmsType.LOGIN);
        return ResponseResult.ok();
    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/register/code")
    public ResponseResult<?> sendRegisterCode(String phone) {
        authSmsService.sendCode(phone, SmsType.REGISTER);
        return ResponseResult.ok();
    }

    /**
     * 是否开启短信功能
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启短信功能")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        boolean smsEnabled = authSmsService.smsEnabled();
        return ResponseResult.ok(smsEnabled);
    }

}
