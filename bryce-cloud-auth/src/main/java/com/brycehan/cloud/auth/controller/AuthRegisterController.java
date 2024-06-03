package com.brycehan.cloud.auth.controller;

import com.brycehan.cloud.api.system.dto.RegisterDto;
import com.brycehan.cloud.auth.service.AuthRegisterService;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.base.http.UserResponseStatus;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统注册API
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Tag(name = "注册")
@RequestMapping(path = "/register")
@RestController
@RequiredArgsConstructor
public class AuthRegisterController {

    private final AuthRegisterService authRegisterService;

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return 响应结果
     */
    @Operation(summary = "注册")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping
    public ResponseResult<Void> register(@Parameter(description = "注册参数", required = true) @Validated @RequestBody RegisterDto registerDto) {
        // 查询注册开关
        if (this.authRegisterService.captchaEnabled()) {
            // 注册
            this.authRegisterService.register(registerDto);
            return ResponseResult.ok();
        }

        return ResponseResult.error(UserResponseStatus.USER_REGISTER_NOT_ENABLED);
    }

    /**
     * 获取注册开关
     *
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "获取注册开关")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> registerEnabled() {
        boolean captchaEnabled = this.authRegisterService.captchaEnabled();
        return ResponseResult.ok(captchaEnabled);
    }

    /**
     * 校验用户账号是否可注册
     *
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户账号是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/check/{username}")
    public ResponseResult<Boolean> checkUsername(@PathVariable String username) {
        boolean checked = this.authRegisterService.checkUsernameUnique(username);
        return ResponseResult.ok(checked);
    }

}

