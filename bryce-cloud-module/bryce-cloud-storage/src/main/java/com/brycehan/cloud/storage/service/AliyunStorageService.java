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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        AliyunStorageProperties aliyun = this.storageProperties.getAliyun();
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
    public ResponseEntity<byte[]> download(String path, String filename) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();

        OSSObject object = null;
        try {// 获取对象
            object = ossClient.getObject(new GetObjectRequest(aliyun.getBucketName(), path));
        } catch (Exception e) {
            log.error("Aliyun OSS 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "下载文件不存在");
        // 将文件输出到Response
        try (InputStream inputStream = object.getObjectContent()) {
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, StandardCharsets.UTF_8));
            headers.setAccessControlExposeHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));
            headers.setContentLength(object.getResponse().getContentLength());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStream.readAllBytes());
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }
}
