package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.vo.MaUserVo;
import com.brycehan.cloud.common.core.ServerNames;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 微信小程序Api
 *
 * @since 2024/4/7
 * @author Bryce Han
 */
@Primary
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "maUser")
public interface MaUserApi {

    /**
     * 获取微信小程序用户账号
     *
     * @param openid 微信openid
     * @return 参数对象
     */
    @GetMapping(path = "/api/maUser/load/{openid}")
    ResponseResult<MaUserVo> loadMaUserByOpenid(@PathVariable String openid);

}