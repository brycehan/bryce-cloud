package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.vo.MaUserVo;
import com.brycehan.cloud.common.core.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 微信小程序Api
 *
 * @since 2024/4/7
 * @author Bryce Han
 */
public interface MaUserApi {

    String PATH = "/api/maUser";

    /**
     * 获取微信小程序用户账号
     *
     * @param openid 微信openid
     * @return 参数对象
     */
    @GetMapping(path = "/load/{openid}")
    ResponseResult<MaUserVo> loadMaUserByOpenid(@PathVariable String openid);

}
