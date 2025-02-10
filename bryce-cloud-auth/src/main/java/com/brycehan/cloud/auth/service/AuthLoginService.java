package com.brycehan.cloud.auth.service;

import com.brycehan.cloud.common.core.entity.dto.AccountLoginDto;
import com.brycehan.cloud.common.core.entity.dto.PhoneLoginDto;
import com.brycehan.cloud.common.core.entity.vo.LoginVo;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import com.brycehan.cloud.common.core.enums.OperateStatus;

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

    /**
     * 保存登录日志
     *
     * @param username 用户名
     * @param status   状态
     * @param info     登录信息
     */
    void saveLoginLog(String username, OperateStatus status, LoginStatus info);
}
