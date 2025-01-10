package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.brycehan.cloud.storage.config.properties.TencentStorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.InputStream;

/**
 * 腾讯云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class TencentStorageService extends StorageService {

    private final COSCredentials credentials;

    private final ClientConfig clientConfig;

    public TencentStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        TencentStorageProperties tencent = storageProperties.getTencent();
        credentials = new BasicCOSCredentials(tencent.getAccessKey(), tencent.getSecretKey());

        clientConfig = new ClientConfig(new Region(tencent.getRegion()));
        clientConfig.setHttpProtocol(HttpProtocol.https);
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        TencentStorageProperties tencent = this.storageProperties.getTencent();
        COSClient client = new COSClient(credentials, clientConfig);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.available());

            PutObjectRequest request = new PutObjectRequest(tencent.getBucketName(),
                    path, data, metadata);
            PutObjectResult result = client.putObject(request);

            if(result.getETag() == null) {
                throw new ServerException("上传文件失败，请检查配置信息");
            }
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        } finally {
            client.shutdown();
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
