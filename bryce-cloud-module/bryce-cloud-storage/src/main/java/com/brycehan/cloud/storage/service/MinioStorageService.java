package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.MinioStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * Minio存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class MinioStorageService extends StorageService {

    private final MinioClient minioClient;

    public MinioStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        MinioStorageProperties minio = storageProperties.getMinio();
        this.minioClient = MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        MinioStorageProperties minio = this.storageProperties.getMinio();
        boolean bucketExists;

        try {
            // 查询bucketName是否存在
            bucketExists = this.minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minio.getBucketName())
                    .build());
            // 如果bucketName不存在，则创建
            if(!bucketExists) {
                this.minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minio.getBucketName())
                        .build());
            }

            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
            if(mediaType.isPresent()) {
                contentType = mediaType.get().toString();
            }

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .contentType(contentType)
                            .object(path)
                            .stream(data, data.available(), -1)
                    .build()
            );
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        return minio.getEndpoint()
                .concat("/").concat(minio.getBucketName())
                .concat("/").concat(path);
    }

    @Override
    public ResponseEntity<byte[]> download(String path, String filename) {
        MinioStorageProperties minio = this.storageProperties.getMinio();

        GetObjectResponse object = null;
        StatObjectResponse statObjectResponse = null;
        try {// 获取对象
            object = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .object(path)
                            .build());
            // 获取对象的元数据
            statObjectResponse = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .object(path)
                            .build());
        } catch (Exception e) {
            log.error("MinIO 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "MinIO 对象不能为空");
        Assert.notNull(statObjectResponse, "MinIO 对象不能为空");
        // 将文件输出到Response
        try (InputStream inputStream = object) {
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, StandardCharsets.UTF_8));
            headers.setAccessControlExposeHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));
            headers.setContentLength(statObjectResponse.size());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStream.readAllBytes());
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }
}
