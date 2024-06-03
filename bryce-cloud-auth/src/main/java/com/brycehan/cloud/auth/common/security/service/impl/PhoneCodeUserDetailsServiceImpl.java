package com.brycehan.cloud.auth.common.security.service.impl;

import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.auth.common.security.service.PhoneCodeUserDetailsService;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 手机验证码登录，用户详情服务实现
 *
 * @author Bryce Han
 * @since 2023/10/7
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneCodeUserDetailsServiceImpl implements PhoneCodeUserDetailsService {

    private final SysUserApi sysUserApi;

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        // 查询用户
        ResponseResult<LoginUser> loginUserResponseResult = this.sysUserApi.loadUserByPhone(phone);

        if (loginUserResponseResult.getCode() == 200 && loginUserResponseResult.getData() == null) {
            log.debug("登录用户：手机号{}不存在.", phone);
            throw new UsernameNotFoundException("手机号或验证码错误");
        }

        // 用户详情
        return loginUserResponseResult.getData();
    }
}
