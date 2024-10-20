package com.brycehan.cloud.api.system.api;

import com.brycehan.cloud.api.system.entity.dto.SysLoginLogDto;
import com.brycehan.cloud.common.core.response.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统登录日志Api
 *
 * @since 2024/5/11
 * @author Bryce Han
 */
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
