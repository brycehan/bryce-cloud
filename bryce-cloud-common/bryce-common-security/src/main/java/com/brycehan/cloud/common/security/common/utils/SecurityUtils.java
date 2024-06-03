package com.brycehan.cloud.common.security.common.utils;

import com.brycehan.cloud.api.system.entity.vo.MaUserVo;
import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import org.springframework.beans.BeanUtils;

import java.util.Collections;

/**
 * @author Bryce Han
 * @since 2024/4/7
 */
public class SecurityUtils {

    /**
     * 创建登录用户
     *
     * @param maUserApiVo 小程序用户
     * @return 登录用户
     */
    public static LoginUser createLoginUser(MaUserVo maUserApiVo) {
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(maUserApiVo, loginUser);
        loginUser.setSourceClient(SourceClientType.MINI_APP.value());
        loginUser.setAuthoritySet(Collections.singleton(DataConstants.ROLE_PREFIX + "miniApp"));

        return loginUser;
    }

    /**
     * 创建登录用户
     *
     * @param sysUserApiVo 系统用户
     * @return 登录用户
     */
    public static LoginUser createLoginUser(SysUserVo sysUserApiVo) {
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUserApiVo, loginUser);
        loginUser.setSourceClient(SourceClientType.APP.value());
        loginUser.setAuthoritySet(Collections.singleton(DataConstants.ROLE_PREFIX + "app"));

        return loginUser;
    }

}
