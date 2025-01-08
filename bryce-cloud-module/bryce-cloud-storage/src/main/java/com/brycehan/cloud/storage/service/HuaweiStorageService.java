package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.InputStream;

/**
 * 华为云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class HuaweiStorageService extends StorageService {

    public HuaweiStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        HuaweiStorageProperties huawei = this.storageProperties.getHuawei();
        ObsClient client = new ObsClient(huawei.getAccessKey(),
                huawei.getSecretKey(),
                huawei.getEndPoint());

        try (client) {
            client.putObject(huawei.getBucketName(), path, data);
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }

    @Override
    public ResponseEntity<byte[]> download(String url, String name, AccessType accessType) {
        return null;
    }
}
