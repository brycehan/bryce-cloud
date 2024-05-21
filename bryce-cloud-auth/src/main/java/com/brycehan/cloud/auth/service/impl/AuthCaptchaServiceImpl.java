package com.brycehan.cloud.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.api.system.SysParamApi;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.common.core.base.RedisKeys;
import com.brycehan.cloud.common.security.config.properties.CaptchaProperties;
import com.brycehan.cloud.common.security.utils.TokenUtils;
import com.brycehan.cloud.auth.vo.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Service
@RequiredArgsConstructor
public class AuthCaptchaServiceImpl implements AuthCaptchaService {

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamApi sysParamApi;

    private final CaptchaProperties captchaProperties;

    @Override
    public CaptchaVo generate() {
        // 生成验证码 key
        String key = TokenUtils.uuid();

        // 生成验证码
        SpecCaptcha captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        captcha.setLen(captchaProperties.getLength());
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        String captchaKey = RedisKeys.getCaptchaKey(key);
        String captchaValue = captcha.text();

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(captchaKey, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);

        // 封装返回数据
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setKey(key);
        captchaVo.setImage(captcha.toBase64());

        return captchaVo;
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
        return this.sysParamApi.getBoolean("system.account.captchaEnabled");
    }
}
