package com.brycehan.cloud.auth.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.brycehan.cloud.api.system.entity.dto.SysLoginLogDto;
import com.brycehan.cloud.auth.common.CaptchaType;
import com.brycehan.cloud.auth.common.security.PhoneCodeAuthenticationToken;
import com.brycehan.cloud.auth.service.AuthCaptchaService;
import com.brycehan.cloud.auth.service.AuthLoginService;
import com.brycehan.cloud.auth.service.AuthPasswordRetryService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.constant.MQConstants;
import com.brycehan.cloud.common.core.entity.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.entity.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.entity.vo.LoginVo;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import com.brycehan.cloud.common.core.enums.OperateStatus;
import com.brycehan.cloud.common.core.util.IpUtils;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.common.core.util.LocationUtils;
import com.brycehan.cloud.common.core.util.ServletUtils;
import com.brycehan.cloud.common.security.jwt.JwtTokenProvider;
import com.brycehan.cloud.common.server.common.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLoginServiceImpl implements AuthLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RabbitTemplate rabbitTemplate;
    private final AuthCaptchaService authCaptchaService;
    private final AuthPasswordRetryService authPasswordRetryService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginVo loginByAccount(AccountLoginDto accountLoginDto) {
        log.debug("loginByAccount，账号认证");
        // 校验验证码
        boolean validated = authCaptchaService.validate(accountLoginDto.getUuid(), accountLoginDto.getCode(), CaptchaType.LOGIN);
        if (!validated) {
            // 保存登录日志
            saveLoginLog(accountLoginDto.getUsername(), OperateStatus.FAIL, LoginStatus.CAPTCHA_FAIL);
            throw new ServerException("验证码错误");
        }

        // 设置记住我
        LoginUserContext.rememberMeHolder.set(Boolean.TRUE.equals(accountLoginDto.getRememberMe()));

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword()));
        } catch (AuthenticationException e) {
            log.warn("loginByAccount，登录认证失败，{}", e.getMessage());
            // 添加密码错误重试缓存
            authPasswordRetryService.retryCount(accountLoginDto.getUsername());
            throw new RuntimeException("用户名或密码错误");
        }

        // 清除密码错误重试缓存
        authPasswordRetryService.deleteCount(accountLoginDto.getUsername());

        return loadLoginVo(authentication);
    }

    @Override
    public LoginVo loginByPhone(PhoneLoginDto phoneLoginDto) {
        // 设置记住我
        LoginUserContext.rememberMeHolder.set(Boolean.TRUE.equals(phoneLoginDto.getRememberMe()));

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("loginByPhone，认证失败，{}", e.getMessage());
            throw new ServerException("手机号或验证码错误");
        }

        return loadLoginVo(authentication);
    }

    /**
     * 通过认证信息获取登录Vo
     *
     * @param authentication 认证信息
     * @return 登录Vo
     */
    private LoginVo loadLoginVo(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成 jwt
        String token = jwtTokenProvider.generateToken(loginUser);

        // 缓存 loginUser
        jwtTokenProvider.cache(loginUser);

        // 设置登录信息
        LoginUserContext.setUserKey(loginUser.getUserKey());
        LoginUserContext.setUserData(JsonUtils.writeValueAsString(loginUser));
        LoginUserContext.setSourceClient(loginUser.getSourceClientType());

        // 封装 LoginVo
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(loginUser, loginVo);
        loginVo.setAccessToken(JwtConstants.TOKEN_PREFIX.concat(token));
        loginVo.setExpiresIn(Duration.between(loginUser.getLoginTime(), loginUser.getExpireTime()).toSeconds());

        return loginVo;
    }

    @Override
    public void refreshToken() {
        jwtTokenProvider.doRefreshToken(LoginUserContext.currentUser());
    }

    @Override
    public void logout() {
        LoginUser loginUser = LoginUserContext.currentUser();
        if (Objects.isNull(loginUser)) {
            return;
        }

        if (StringUtils.isNotEmpty(loginUser.getUserKey())) {
            // 删除登录用户缓存记录
            jwtTokenProvider.deleteLoginUser(loginUser.getUserKey());
        }

        // 记录用户退出日志
        saveLoginLog(loginUser.getUsername(), OperateStatus.SUCCESS, LoginStatus.LOGOUT_SUCCESS);
    }

    @Override
    public void saveLoginLog(String username, OperateStatus status, LoginStatus info) {

        // 记录登录日志
        SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
        sysLoginLogDto.setId(IdGenerator.nextId());
        sysLoginLogDto.setUsername(username);
        sysLoginLogDto.setStatus(status);
        sysLoginLogDto.setInfo(info);

        // 获取客户端信息
        String userAgent = ServletUtils.getRequest().getHeader(HttpHeaders.USER_AGENT);
        UserAgent parser = UserAgentUtil.parse(userAgent);

        // 获取客户端操作系统
        String os = parser.getOs().getName();
        // 获取客户端浏览器
        String browser = parser.getBrowser().getName();

        // 获取客户端IP和对应登录位置
        String ip = IpUtils.getIp(ServletUtils.getRequest());
        String loginLocation = LocationUtils.getLocationByIP(ip);

        sysLoginLogDto.setUserAgent(userAgent);
        sysLoginLogDto.setOs(os);
        sysLoginLogDto.setBrowser(browser);
        sysLoginLogDto.setIp(ip);
        sysLoginLogDto.setLocation(loginLocation);

        sysLoginLogDto.setAccessTime(LocalDateTime.now());
        sysLoginLogDto.setCreatedTime(LocalDateTime.now());

        // 保存数据
        rabbitTemplate.convertAndSend(MQConstants.LOGIN_LOG_EXCHANGE, MQConstants.LOGIN_LOG_CREATE_ROUTING_KEY, JsonUtils.writeValueAsString(sysLoginLogDto));
    }
}
