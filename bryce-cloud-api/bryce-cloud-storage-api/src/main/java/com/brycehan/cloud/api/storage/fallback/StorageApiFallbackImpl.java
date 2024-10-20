package com.brycehan.cloud.api.storage.fallback;

import com.brycehan.cloud.api.storage.client.StorageClient;
import com.brycehan.cloud.common.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 存储服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class StorageApiFallbackImpl implements FallbackFactory<StorageClient> {

    @Override
    public StorageClient create(Throwable cause) {
        log.error("邮件服务调用失败，{}", cause.getMessage());
        return file -> ResponseResult.fallback("存储服务调用失败");
    }
}

