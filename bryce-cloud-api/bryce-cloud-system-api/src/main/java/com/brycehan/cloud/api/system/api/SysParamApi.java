package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.dto.SysParamDto;
import com.brycehan.cloud.api.system.entity.vo.SysParamApiVo;
import com.brycehan.cloud.api.system.fallback.SysParamApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.response.ResponseResult;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@Headers(DataConstants.INNER_CALL_HEADER)
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysParamApi.PATH, contextId = "sysParam", fallbackFactory = SysParamApiFallbackImpl.class)
public interface SysParamApi {

    String PATH = "/api/sysParam";

    /**
     * 添加系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @PostMapping
    ResponseResult<Void> save(@RequestBody SysParamDto sysParamDto);

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @PutMapping
    ResponseResult<Void> update(@RequestBody SysParamDto sysParamDto);

    /**
     * 判断 paramKey 是否存在
     *
     * @param paramKey 参数key
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/exists")
    ResponseResult<Boolean> exists(@RequestParam String paramKey);

    /**
     * 获取参数对象
     *
     * @param paramKey 参数key
     * @return 参数对象
     */
    @GetMapping(path = "/getByParamKey")
    ResponseResult<SysParamApiVo> getByParamKey(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询字符串类型的参数值
     *
     * @param paramKey 参数key
     * @return 参数值
     */
    @GetMapping(path = "/getString")
    ResponseResult<String> getString(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询boolean类型的参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    @GetMapping(path = "/getBoolean")
    ResponseResult<Boolean> getBoolean(@RequestParam String paramKey);

}
