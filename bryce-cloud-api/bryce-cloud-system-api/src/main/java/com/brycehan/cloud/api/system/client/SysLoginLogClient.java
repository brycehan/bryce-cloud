package com.brycehan.cloud.api.system.client;

import com.brycehan.cloud.api.system.api.SysLoginLogApi;
import com.brycehan.cloud.api.system.fallback.SysLoginLogApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysLoginLogApi.PATH, contextId = "sysLoginLog", fallbackFactory = SysLoginLogApiFallbackImpl.class)
public interface SysLoginLogClient extends SysLoginLogApi {

}
