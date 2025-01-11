package com.brycehan.cloud.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 七牛云存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.qiniu")
public class QiniuStorageProperties {

    private String domain;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
