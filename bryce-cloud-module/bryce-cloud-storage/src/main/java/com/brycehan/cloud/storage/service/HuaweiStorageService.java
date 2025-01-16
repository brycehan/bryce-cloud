package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 华为云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class HuaweiStorageService extends StorageService {

    private final ObsClient obsClient;

    public HuaweiStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        HuaweiStorageProperties huawei = storageProperties.getHuawei();
        // 初始化obsClient实例
        obsClient = new ObsClient(huawei.getAccessKey(),
                huawei.getSecretKey(),
                huawei.getEndPoint());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        HuaweiStorageProperties huawei = storageProperties.getHuawei();

        try (obsClient) {
            obsClient.putObject(huawei.getBucketName(), path, data);
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public byte[] download(String path) {
        HuaweiStorageProperties huawei = storageProperties.getHuawei();

        ObsObject object = null;
        try {// 获取对象
            object = obsClient.getObject(huawei.getBucketName(), path);
        } catch (Exception e) {
            log.error("Huawei Obs 连接出错：{}", e.getMessage());
        }

        // 判断文件是否存在
        if (object == null) {
            return null;
        }

        // 获取文件的字节数组
        return getByteArrayByInputStream(object.getObjectContent());
    }
}
