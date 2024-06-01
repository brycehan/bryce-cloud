package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.storage.config.properties.QiniuStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.File;
import java.io.InputStream;

/**
 * 七牛云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class QiniuStorageService extends StorageService {

    private final UploadManager uploadManager;

    private final String token;

    public QiniuStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        QiniuStorageProperties qiniu = storageProperties.getQiniu();

        this.uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        token = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey()).uploadToken(qiniu.getBucketName());
    }

    @Override
    public String upload(InputStream data, String path) {
        try {
            Response response = this.uploadManager.put(IOUtils.toByteArray(data), path, token);
            if(!response.isOK()) {
                throw new ServerException(response.toString());
            }
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }
}
