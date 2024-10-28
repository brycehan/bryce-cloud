package com.brycehan.cloud.api.sms.client;

import com.brycehan.cloud.api.sms.api.SmsApi;
import com.brycehan.cloud.api.sms.fallback.SmsApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Bryce Han
 * @since 2024/10/20
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_SMS, path = SmsApi.PATH, contextId = "sms", fallbackFactory = SmsApiFallbackImpl.class)
public interface SmsClient extends SmsApi {
}
