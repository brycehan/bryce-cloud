package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.QiniuStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 七牛云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class QiniuStorageService extends StorageService {

    private final UploadManager uploadManager;
    private final Auth auth;
    private final String token;
    private final RestClient restClient = RestClient.builder().build();

    public QiniuStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        QiniuStorageProperties qiniu = storageProperties.getQiniu();

        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        token = auth.uploadToken(qiniu.getBucketName());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        try {
            Response response = uploadManager.put(IOUtils.toByteArray(data), path, token);
            if(!response.isOK()) {
                throw new ServerException(response.toString());
            }
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public byte[] download(String path) {
        QiniuStorageProperties qiniu = storageProperties.getQiniu();

        try {// 获取下载链接
            String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            DownloadUrl downloadUrl = new DownloadUrl(qiniu.getDomain(), true, encodedPath);
            // 带有效期1小时
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
            // 生成下载链接
            String url = downloadUrl.buildURL(auth, deadline);
            return restClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .body(byte[].class);
        }catch (Exception e) {
            throw new ServerException("QINIU Obs 连接出错：", e);
        }
    }
}
