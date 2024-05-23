package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.dto.SysParamDto;
import com.brycehan.cloud.api.system.vo.SysParamApiVo;
import com.brycehan.cloud.common.core.ServerNames;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sysParam")
public interface SysParamApi {

    /**
     * 添加系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @PostMapping(path = "/api/param")
    ResponseResult<Void> save(@RequestBody SysParamDto sysParamDto);

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @PutMapping(path = "/api/param")
    ResponseResult<Void> update(@RequestBody SysParamDto sysParamDto);

    /**
     * 判断 paramKey 是否存在
     *
     * @param paramKey 参数key
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/api/param/exists")
    ResponseResult<Boolean> exists(@RequestParam String paramKey);

    /**
     * 获取参数对象
     *
     * @param paramKey 参数key
     * @return 参数对象
     */
    @GetMapping(path = "/api/param/getByParamKey")
    ResponseResult<SysParamApiVo> getByParamKey(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询字符串类型的参数值
     *
     * @param paramKey 参数key
     * @return 参数值
     */
    @GetMapping(path = "/api/param/getString")
    ResponseResult<String> getString(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询boolean类型的参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    @GetMapping(path = "/api/param/getBoolean")
    ResponseResult<Boolean> getBoolean(@RequestParam String paramKey);

}
