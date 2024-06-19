package com.brycehan.cloud.auth.service;

import com.brycehan.cloud.common.core.entity.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.entity.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.entity.vo.LoginVo;

/**
 * 认证服务
 *
 * @since 2022/9/16
 * @author Bryce Han
 */

public interface AuthLoginService {

    /**
     * 账号登录
     *
     * @param accountLoginDto 账号登录dto
     * @return 登录Vo
     */
    LoginVo loginByAccount(AccountLoginDto accountLoginDto);

    /**
     * 手机号登录
     *
     * @param phoneLoginDto 手机号登录dto
     * @return 登录Vo
     */
    LoginVo loginByPhone(PhoneLoginDto phoneLoginDto);

    /**
     * 刷新token
     */
    void refreshToken();

    /**
     * 退出登录
     */
    void logout();

}
