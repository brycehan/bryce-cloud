package com.brycehan.cloud.api.system.client;

import com.brycehan.cloud.api.system.api.SysAreaCodeApi;
import com.brycehan.cloud.api.system.fallback.SysAreaCodeApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysAreaCodeApi.PATH, contextId = "sysArea", fallbackFactory = SysAreaCodeApiFallbackImpl.class)
public interface SysAreaCodeClient extends SysAreaCodeApi {

}
