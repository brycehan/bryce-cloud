package com.brycehan.cloud.storage.service;

import cn.hutool.core.io.IoUtil;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.LocalStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
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
        return storageProperties.getConfig().getEndpoint()
                .concat("/").concat(local.getPrefix())
                .concat("/").concat(path);
    }

    @Override
    public byte[] download(String path) {
        LocalStorageProperties local = storageProperties.getLocal();
        File file = new File(local.getAccessPath(path));

        if (!file.exists()) {
            return null;
        }

        // 获取文件的字节数组
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Files.copy(file.toPath(), outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ServerException("下载文件出错：", e);
        }
    }
}
