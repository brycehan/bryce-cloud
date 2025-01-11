package com.brycehan.cloud.storage.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.LocalStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class LocalStorageService extends StorageService {

    public LocalStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {

        LocalStorageProperties local = storageProperties.getLocal();
        try {
            File file = new File(local.getAccessPath(path));

            // 没有目录，则自动创建目录
            File parent = file.getParentFile();
            if(parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("目录".concat(parent.toString()).concat("创建失败"));
            }

            IoUtil.copy(data, Files.newOutputStream(file.toPath()), 1024 * 1024);
        } catch (Exception e) {
            throw new ServerException("上传文件失败：", e);
        }

        // 安全访问的相对路径
        if (accessType == AccessType.SECURE) {
            return "";
        }
        // 公共访问路径
        return this.storageProperties.getConfig().getEndpoint()
                .concat("/").concat(local.getPrefix())
                .concat("/").concat(path);
    }

    @Override
    public ResponseEntity<byte[]> download(String path, String filename) {
        LocalStorageProperties local = this.storageProperties.getLocal();
        File file = new File(local.getAccessPath(path));

        // 获取文件名
        if (StrUtil.isBlank(filename)) {
            filename = StrUtil.subAfter(path, "/", true).split("_")[0];
            if (StrUtil.isBlank(filename)) {
                filename = "download";
            }
        }

        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }

        // 将文件输出到Response
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            byte[] data = IoUtil.readBytes(inputStream);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, StandardCharsets.UTF_8));
            headers.setAccessControlExposeHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));
            headers.setContentLength(data.length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(data);
        } catch (IOException e) {
            throw new RuntimeException("下载文件失败：", e);
        }
    }
}
