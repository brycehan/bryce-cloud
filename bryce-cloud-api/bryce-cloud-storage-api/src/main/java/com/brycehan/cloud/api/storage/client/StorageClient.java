package com.brycehan.cloud.api.storage.client;

import com.brycehan.cloud.api.storage.api.StorageApi;
import com.brycehan.cloud.api.storage.fallback.StorageApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_STORAGE, path = StorageApi.PATH, contextId = "storage", fallbackFactory = StorageApiFallbackImpl.class)
public interface StorageClient extends StorageApi {
}
