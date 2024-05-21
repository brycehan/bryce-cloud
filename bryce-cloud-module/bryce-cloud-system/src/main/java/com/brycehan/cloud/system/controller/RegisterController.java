package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.dto.RegisterDto;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.base.http.UserResponseStatus;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.system.service.SysParamService;
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
public class RegisterController {

    private final SysParamService sysParamService;

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
        // 1、查询注册开关
        boolean registerEnabled = this.sysParamService.getBoolean("system.account.registerEnabled");
        if (registerEnabled) {
            // 2、注册
//            this.sysRegisterService.register(registerDto);
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
    @GetMapping(path = "enabled")
    public ResponseResult<Boolean> registerEnabled() {
        boolean registerEnabled = this.sysParamService.getBoolean("system.account.registerEnabled");
        return ResponseResult.ok(registerEnabled);
    }

}

