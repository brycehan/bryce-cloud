package com.brycehan.cloud.api.storage.client;

import com.brycehan.cloud.api.storage.api.StorageApi;
import com.brycehan.cloud.api.storage.fallback.StorageApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.constant.DataConstants;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@Headers(DataConstants.INNER_CALL_HEADER)
@FeignClient(name = ServerNames.BRYCE_CLOUD_STORAGE, path = StorageApi.PATH, contextId = "storage", fallbackFactory = StorageApiFallbackImpl.class)
public interface StorageClient extends StorageApi {
}
