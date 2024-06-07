package com.brycehan.cloud.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.auth.entity.vo.SmsCodeVo;
import com.brycehan.cloud.auth.service.AuthSmsService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.RedisKeys;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.constant.ParamConstants;
import com.brycehan.cloud.common.core.enums.SmsType;
import com.brycehan.cloud.common.security.common.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthSmsServiceImpl implements AuthSmsService {

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamApi sysParamApi;

    private final SmsApi smsApi;

    private final SysUserApi sysUserApi;

    private final long expiration = 5;

    @Override
    public SmsCodeVo sendCode(String phone, SmsType smsType) {
        if (!this.smsEnabled()) {
            throw new RuntimeException("短信功能未开启");
        }

        if (!this.smsEnabled(smsType)) {
            throw new RuntimeException(smsType.desc() + "短信功能未开启");
        }

        ResponseResult<LoginUser> loginUserResponseResult = this.sysUserApi.loadUserByPhone(phone);
        if(loginUserResponseResult.getData() == null) {
            throw new ServerException("手机号码未注册");
        }

        // 生成验证码 key
        String codeKey = TokenUtils.uuid();

        // 生成6位验证码
        String codeValue = RandomStringUtils.randomNumeric(6);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", codeValue);

        // 发送短信
        ResponseResult<Boolean> send = this.smsApi.send(phone, smsType, params);
        if (send.getCode() != 200) {
            log.error("短信发送失败，手机号码：{}，验证码类型：{}，验证码：{}，原因：{}", phone, smsType, codeValue, send.getMessage());
            throw new ServerException(send.getMessage().split("，")[0]);
        }

        log.debug("短信验证码key：{}, 值：{}", codeKey, codeValue);

        String smsCodeKey = RedisKeys.getSmsCodeKey(codeKey, smsType);

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(smsCodeKey, codeValue, this.expiration, TimeUnit.MINUTES);

        // 封装返回数据
        SmsCodeVo smsCodeVo = new SmsCodeVo();
        smsCodeVo.setKey(codeKey);
        return smsCodeVo;
    }

    @Override
    public boolean validate(String key, String code, SmsType smsType) {
        // 如果关闭了验证码，则直接校验通过
        if (!smsEnabled(smsType)) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String smsCodeKey = RedisKeys.getSmsCodeKey(key, smsType);
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(smsCodeKey);

        // 校验
        return code.equalsIgnoreCase(captchaValue);
    }

    @Override
    public boolean smsEnabled() {
        ResponseResult<Boolean> responseResult = this.sysParamApi.getBoolean(ParamConstants.SYSTEM_SMS_ENABLED);
        return responseResult.getData();
    }

    @Override
    public boolean smsEnabled(SmsType smsType) {
        if (smsType == null) {
            return false;
        }
        if (SmsType.LOGIN.equals(smsType)) {
            ResponseResult<Boolean> responseResult = this.sysParamApi.getBoolean(ParamConstants.SYSTEM_LOGIN_SMS_ENABLED);
            return responseResult.getData();
        } else if (SmsType.REGISTER.equals(smsType)) {
            ResponseResult<Boolean> responseResult = this.sysParamApi.getBoolean(ParamConstants.SYSTEM_REGISTER_SMS_ENABLED);
            return responseResult.getData();
        }

        return false;
    }
}
