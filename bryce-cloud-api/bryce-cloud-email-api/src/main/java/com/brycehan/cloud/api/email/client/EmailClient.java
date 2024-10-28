package com.brycehan.cloud.api.email.client;

import com.brycehan.cloud.api.email.api.EmailApi;
import com.brycehan.cloud.api.email.fallback.EmailApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_EMAIL, path = EmailApi.PATH, contextId = "email", fallbackFactory = EmailApiFallbackImpl.class)
public interface EmailClient extends EmailApi {
}
