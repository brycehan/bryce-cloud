package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.dto.SysLoginLogDto;
import com.brycehan.cloud.api.system.fallback.SysLoginLogApiFallbackImpl;
import com.brycehan.cloud.common.core.ServerNames;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统登录日志Api
 *
 * @since 2024/5/11
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysLoginLogApi.PATH, contextId = "sysLoginLog", fallbackFactory = SysLoginLogApiFallbackImpl.class)
public interface SysLoginLogApi {

    String PATH = "/api/sysLoginLog";

    /**
     * 保存登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    @PostMapping
    ResponseResult<Void> save(@RequestBody SysLoginLogDto sysLoginLogDto);

}
