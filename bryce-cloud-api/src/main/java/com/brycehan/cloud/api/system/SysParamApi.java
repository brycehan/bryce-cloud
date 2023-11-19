package com.brycehan.cloud.api.system;

import com.brycehan.cloud.api.ServerNames;
import com.brycehan.cloud.api.system.dto.SysParamApiDto;
import com.brycehan.cloud.api.system.vo.SysParamApiVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sysParam")
public interface SysParamApi {

    /**
     * 添加系统参数
     *
     * @param sysParamApiDto 系统参数Dto
     */
    @PostMapping(path = "/api/system/param")
    void save(@RequestBody SysParamApiDto sysParamApiDto);

    /**
     * 更新系统参数
     *
     * @param sysParamApiDto 系统参数Dto
     */
    @PutMapping(path = "/api/system/param")
    void update(@RequestBody SysParamApiDto sysParamApiDto);

    /**
     * 判断 paramKey 是否存在
     *
     * @param paramKey 参数key
     *
     * @return paramKey 是否存在
     */
    @GetMapping(path = "/api/system/param/exists")
    Boolean exists(@RequestParam String paramKey);

    /**
     * 获取参数对象
     *
     * @param paramKey 参数key
     * @return 参数对象
     */
    @GetMapping(path = "/api/system/param/getByParamKey")
    SysParamApiVo getByParamKey(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询字符串类型的参数值
     *
     * @param paramKey 参数key
     * @return 参数值
     */
    @GetMapping(path = "/api/system/param/getString")
    String getString(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询boolean类型的参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    @GetMapping(path = "/api/system/param/getBoolean")
    Boolean getBoolean(@RequestParam String paramKey);

}
