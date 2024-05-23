package com.brycehan.cloud.auth.controller;

import com.brycehan.cloud.api.sms.enums.SmsType;
import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

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

    private final SmsApi smsApi;

    private final SysUserApi sysUserApi;

    /**
     * 生成登录验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成登录验证码")
    @GetMapping(path = "/login/code")
    public ResponseResult<?> sendLoginCode(String phone) {
        return sendCode(phone, SmsType.LOGIN);
    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/register/code")
    public ResponseResult<?> sendRegisterCode(String phone) {
        return sendCode(phone, SmsType.REGISTER);
    }

    /**
     * 是否开启短信功能
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启短信功能")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        ResponseResult<Boolean> responseResult = this.smsApi.isSmsEnabled();
        if (responseResult.getCode() != 200) {
            return responseResult;
        }
        return ResponseResult.ok(responseResult.getData());
    }

    @NotNull
    private ResponseResult<?> sendCode(String phone, SmsType smsType) {
        ResponseResult<Boolean> responseResult = this.smsApi.isSmsEnabled();

        if (responseResult.getCode() != 200) {
            return responseResult;
        }

        boolean smsEnabled = responseResult.getData();
        if (!smsEnabled) {
            return ResponseResult.error("短信功能未开启");
        }

        ResponseResult<LoginUser> loginUserResponseResult = this.sysUserApi.loadUserByPhone(phone);
        if(loginUserResponseResult.getData() == null) {
            throw new RuntimeException("手机号码未注册");
        }

        // 生成6位验证码
        String code = RandomStringUtils.randomNumeric(6);
        
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", code);

        // 发送短信
        ResponseResult<Boolean> send = this.smsApi.send(phone, smsType, params);
        if (send.getCode() != 200) {
            throw new RuntimeException("短信发送失败".concat(send.getMessage()));
        }

        return ResponseResult.ok(send.getData());
    }

}
