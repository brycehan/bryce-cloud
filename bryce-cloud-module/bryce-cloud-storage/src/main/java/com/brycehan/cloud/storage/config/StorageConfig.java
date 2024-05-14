package com.brycehan.cloud.storage.config;

import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.brycehan.cloud.storage.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(prefix = "bryce.storage", value = "enabled")
public class StorageConfig {

    /**
     * 存储服务
     *
     * @param storageProperties 存储属性
     * @return 存储服务
     */
    @Bean
    public StorageService storageService(StorageProperties storageProperties) {
        return switch (storageProperties.getConfig().getType()) {
            case LOCAL -> new LocalStorageService(storageProperties);
            case MINIO -> new MinioStorageService(storageProperties);
            case ALIYUN -> new AliyunStorageService(storageProperties);
            case TENCENT -> new TencentStorageService(storageProperties);
            case QINIU -> new QiniuStorageService(storageProperties);
            case HUAWEI -> new HuaweiStorageService(storageProperties);
        };
    }
}
