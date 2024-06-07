package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.fallback.SysAreaCodeApiFallbackImpl;
import com.brycehan.cloud.common.core.ServerNames;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 地区编码Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysAreaCodeApi.PATH, contextId = "sysArea", fallbackFactory = SysAreaCodeApiFallbackImpl.class)
public interface SysAreaCodeApi {

    String PATH = "/api/sysArea";

    /**
     * 根据地区编码获取扩展名称
     *
     * @param areaCode 地区编码
     * @return 扩展名称
     */
    @GetMapping(path = "/extNameByCode/{areaCode}")
    ResponseResult<String> getExtNameByCode(@PathVariable @RequestParam String areaCode);

    /**
     * 获取地区位置
     *
     * @param areaCode 地区编码
     * @return 地区位置
     */
    @GetMapping(path = "/fullLocation/{areaCode}")
    ResponseResult<String> getFullLocation(@PathVariable @RequestParam String areaCode);

}
