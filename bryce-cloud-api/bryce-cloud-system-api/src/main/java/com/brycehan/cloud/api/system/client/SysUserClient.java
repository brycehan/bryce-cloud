package com.brycehan.cloud.api.system.client;

import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.fallback.SysUserApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysUserApi.PATH, contextId = "sysUser", fallbackFactory = SysUserApiFallbackImpl.class)
public interface SysUserClient extends SysUserApi {

}
