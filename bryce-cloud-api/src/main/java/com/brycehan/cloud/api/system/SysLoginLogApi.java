package com.brycehan.cloud.api.system;

import com.brycehan.cloud.api.ServerNames;
import com.brycehan.cloud.api.system.dto.SysLoginLogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统登录日志Api
 *
 * @since 2024/5/11
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "sysLoginLog")
public interface SysLoginLogApi {

    /**
     * 保存登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    @PostMapping(path = "/api/loginLog")
    void save(@RequestBody SysLoginLogDto sysLoginLogDto);

}
