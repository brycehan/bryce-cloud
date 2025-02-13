package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 地区编码Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SysAreaCodeApi {

    String PATH = "/api/sysArea";

    /**
     * 根据地区编码获取扩展名称
     *
     * @param areaCode 地区编码
     * @return 扩展名称
     */
    @GetMapping(path = "/extNameByCode/{areaCode}")
    ResponseResult<String> getExtNameByCode(@PathVariable String areaCode);

    /**
     * 获取地区位置
     *
     * @param areaCode 地区编码
     * @return 地区位置
     */
    @GetMapping(path = "/fullLocation/{areaCode}")
    ResponseResult<String> getFullLocation(@PathVariable String areaCode);

}
