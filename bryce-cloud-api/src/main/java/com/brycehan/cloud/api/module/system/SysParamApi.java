package com.brycehan.cloud.api.module.system;

import com.brycehan.cloud.api.module.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统参数 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sysParam")
public interface SysParamApi {

    /**
     * 根据paramKey，查询参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    @GetMapping(path = "/api/system/param/getBoolean")
    boolean getBoolean(@RequestParam String paramKey);

}
