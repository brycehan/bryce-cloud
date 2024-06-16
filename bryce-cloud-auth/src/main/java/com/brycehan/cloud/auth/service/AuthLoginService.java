package com.brycehan.cloud.auth.service;

import com.brycehan.cloud.common.core.base.LoginUser;
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
     * 更新用户登录信息
     *
     * @param loginUser 登录用户
     */
    void updateLoginInfo(LoginUser loginUser);

    /**
     * 退出登录
     *
     * @param loginUser 登录用户
     */
    void logout(LoginUser loginUser);

}
