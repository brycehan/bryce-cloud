package com.brycehan.cloud.api.storage.fallback;

import com.brycehan.cloud.api.storage.client.StorageClient;
import com.brycehan.cloud.api.storage.entity.StorageVo;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.AccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 存储服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StorageApiFallbackImpl implements FallbackFactory<StorageClient> {

    @Override
    public StorageClient create(Throwable cause) {
        log.error("存储服务调用失败", cause);
        return new StorageClient() {
            @Override
            public ResponseResult<StorageVo> upload(MultipartFile file, AccessType accessType) {
                return ResponseResult.fallback("存储服务调用失败");
            }

            @Override
            public ResponseResult<StorageVo> upload(MultipartFile file, AccessType accessType, List<String> allowedExtensions) {
                return ResponseResult.fallback("存储服务调用失败");
            }

            @Override
            public ResponseResult<byte[]> download(String url, String filename) {
                return ResponseResult.fallback("存储服务调用失败");
            }
        };
    }
}

