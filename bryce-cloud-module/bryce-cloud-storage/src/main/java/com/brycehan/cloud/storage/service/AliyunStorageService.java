package com.brycehan.cloud.storage.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.AliyunStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.InputStream;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class AliyunStorageService extends StorageService {

    public AliyunStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        AliyunStorageProperties aliyun = this.storageProperties.getAliyun();
        OSS client = new OSSClientBuilder().build(aliyun.getEndPoint(),
                aliyun.getAccessKeyId(),
                aliyun.getAccessKeySecret());
        try {
            client.putObject(aliyun.getBucketName(), path, data);
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        } finally {
            if(client != null) {
                client.shutdown();
            }
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }

    @Override
    public ResponseEntity<byte[]> download(String path, String filename) {
        return null;
    }
}
