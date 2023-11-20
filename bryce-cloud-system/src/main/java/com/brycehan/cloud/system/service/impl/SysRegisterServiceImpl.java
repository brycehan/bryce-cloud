package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.base.RedisKeys;
import com.brycehan.cloud.common.base.dto.RegisterDto;
import com.brycehan.cloud.common.base.http.UserResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;
import com.brycehan.cloud.common.util.PasswordUtils;
import com.brycehan.cloud.system.entity.SysUser;
import com.brycehan.cloud.system.service.SysParamService;
import com.brycehan.cloud.system.service.SysRegisterService;
import com.brycehan.cloud.system.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 系统注册服务实现类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Service
public class SysRegisterServiceImpl implements SysRegisterService {

    private final SysUserService sysUserService;

    private final SysParamService sysParamService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SysRegisterServiceImpl(SysUserService sysUserService, SysParamService sysParamService) {
        this.sysUserService = sysUserService;
        this.sysParamService = sysParamService;
    }

    @Override
    public void register(RegisterDto registerDto) {
        // 1、验证码开关
        boolean validated = this.validate(registerDto.getKey(), registerDto.getCode());
        if (!validated) {
            throw new RuntimeException("验证码错误");
        }

        // 2、用户账号唯一校验
        SysUser sysUser = new SysUser();
        sysUser.setUsername(registerDto.getUsername().trim());
        boolean usernameUnique = this.sysUserService.checkUsernameUnique(sysUser);
        if (!usernameUnique) {
            throw BusinessException.responseStatus(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
        }
        // 3、注册
        sysUser.setFullName(sysUser.getUsername());
        sysUser.setPassword(PasswordUtils.encode(registerDto.getPassword().trim()));
        this.sysUserService.registerUser(sysUser);
    }

    @Override
    public boolean validate(String key, String code) {
        // 如果关闭了验证码，则直接校验通过
        if (!isCaptchaEnabled()) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String captchaKey = RedisKeys.getCaptchaKey(key);
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(captchaKey);

        // 校验
        return code.equalsIgnoreCase(captchaValue);
    }

    @Override
    public boolean isCaptchaEnabled() {
        return this.sysParamService.getBoolean("system.account.captchaEnabled");
    }

}
