package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.vo.MaUserVo;
import com.brycehan.cloud.api.system.fallback.MaUserApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.response.ResponseResult;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 微信小程序Api
 *
 * @since 2024/4/7
 * @author Bryce Han
 */
@Headers(DataConstants.INNER_CALL_HEADER)
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = MaUserApi.PATH, contextId = "maUser", fallbackFactory = MaUserApiFallbackImpl.class)
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
