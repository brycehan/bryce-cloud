package com.brycehan.cloud.storage.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.AliyunStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class AliyunStorageService extends StorageService {

    private final OSS ossClient;

    public AliyunStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        AliyunStorageProperties aliyun = storageProperties.getAliyun();
        // 初始化OSSClient实例。
        ossClient = new OSSClientBuilder().build(aliyun.getEndPoint(),
                aliyun.getAccessKeyId(),
                aliyun.getAccessKeySecret());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();

        try {
            ossClient.putObject(aliyun.getBucketName(), path, data);
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        } finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public byte[] download(String path) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();

        OSSObject object = null;
        try {// 获取对象
            object = ossClient.getObject(new GetObjectRequest(aliyun.getBucketName(), path));
        } catch (Exception e) {
            log.error("Aliyun OSS 连接出错：{}", e.getMessage());
        }

        // 判断文件是否存在
        if (object == null) {
            return null;
        }

        // 获取文件的字节数组
        return getByteArrayByInputStream(object.getObjectContent());
    }
}
