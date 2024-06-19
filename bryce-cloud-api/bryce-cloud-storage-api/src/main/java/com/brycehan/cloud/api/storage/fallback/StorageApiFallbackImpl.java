package com.brycehan.cloud.api.storage.fallback;

import com.brycehan.cloud.api.storage.api.StorageApi;
import com.brycehan.cloud.api.storage.entity.StorageVo;
import com.brycehan.cloud.common.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class StorageApiFallbackImpl implements FallbackFactory<StorageApi> {

    @Override
    public StorageApi create(Throwable cause) {
        log.error("邮件服务调用失败，{}", cause.getMessage());
        return new StorageApi() {
            @Override
            public ResponseResult<StorageVo> upload(MultipartFile file) {
                return ResponseResult.fallback("存储服务调用失败");
            }
        };
    }
}

