package com.brycehan.cloud.storage.service;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
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
        HuaweiStorageProperties huawei = this.storageProperties.getHuawei();
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
    public ResponseEntity<byte[]> download(String path, String filename) {
        HuaweiStorageProperties huawei = storageProperties.getHuawei();

        ObsObject object = null;
        try {// 获取对象
            object = obsClient.getObject(huawei.getBucketName(), path);
        } catch (Exception e) {
            log.error("Huawei Obs 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "下载文件不存在");
        // 将文件输出到Response
        try (InputStream inputStream = object.getObjectContent()) {
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, StandardCharsets.UTF_8));
            headers.setAccessControlExposeHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));
            headers.setContentLength(object.getMetadata().getContentLength().intValue());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStream.readAllBytes());
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }
}
