package com.brycehan.cloud.api.system.client;

import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.fallback.SysParamApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.constant.DataConstants;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@Headers(DataConstants.INNER_CALL_HEADER)
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, path = SysParamApi.PATH, contextId = "sysParam", fallbackFactory = SysParamApiFallbackImpl.class)
public interface SysParamClient extends SysParamApi {

}
