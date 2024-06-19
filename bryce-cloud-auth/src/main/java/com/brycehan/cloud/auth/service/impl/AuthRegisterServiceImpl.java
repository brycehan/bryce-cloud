package com.brycehan.cloud.auth.service.impl;

import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.entity.dto.RegisterDto;
import com.brycehan.cloud.api.system.entity.dto.SysUserDto;
import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.auth.common.CaptchaType;
import com.brycehan.cloud.auth.common.RegisterSuccessEvent;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.auth.service.AuthRegisterService;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.constant.ParamConstants;
import com.brycehan.cloud.common.core.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 系统注册服务实现类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRegisterServiceImpl implements AuthRegisterService {

    private final SysUserApi sysUserApi;

    private final SysParamApi sysParamApi;

    private final AuthCaptchaService authCaptchaService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void register(RegisterDto registerDto) {
        // 验证码开关
        boolean validated = this.validate(registerDto.getKey(), registerDto.getCode());
        if (!validated) {
            throw new ServerException("验证码错误");
        }

        // 注册
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtils.copyProperties(registerDto, sysUserDto);

        ResponseResult<SysUserVo> responseResult = this.sysUserApi.registerUser(sysUserDto);
        if (!responseResult.getCode().equals(200) || responseResult.getData() == null) {
            throw new ServerException("注册失败，系统内部错误");
        }

        log.info("注册成功，用户名：{}", registerDto.getUsername());
        this.applicationEventPublisher.publishEvent(new RegisterSuccessEvent(responseResult.getData()));
    }

    @Override
    public boolean validate(String key, String code) {
        return this.authCaptchaService.validate(key, code, CaptchaType.REGISTER);
    }

    @Override
    public boolean registerEnabled() {
        ResponseResult<Boolean> responseResult = this.sysParamApi.getBoolean(ParamConstants.SYSTEM_REGISTER_ENABLED);
        return responseResult.getData();
    }

    @Override
    public boolean checkUsernameUnique(String username) {
        ResponseResult<Boolean> loginUserResponseResult = this.sysUserApi.checkUsernameUnique(username);
        return loginUserResponseResult.getCode() == 200 && loginUserResponseResult.getData();
    }

}
