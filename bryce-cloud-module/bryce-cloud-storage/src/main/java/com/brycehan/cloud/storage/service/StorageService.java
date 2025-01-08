package com.brycehan.cloud.storage.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Date;

/**
 * 存储服务
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@SuppressWarnings("unused")
public abstract class StorageService {

    public StorageProperties storageProperties;

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @param accessType 访问类型
     * @return http资源地址
     */
    public String upload(byte[] data, String path, AccessType accessType) {
        return this.upload(new ByteArrayInputStream(data), path, accessType);
    }

    /**
     * 文件上传
     *
     * @param data 文件字节流
     * @param path 文件路径，包含文件名
     * @param accessType 访问类型
     * @return http资源地址
     */
    public abstract String upload(InputStream data, String path, AccessType accessType);

    /**
     * 生成路径，不包含文件名
     * @return 生成的路径
     */
    public String getPath() {
        // 文件路径
        String path = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);

        // 如果有前缀，则也带上
        if(StrUtil.isNotEmpty(storageProperties.getConfig().getPrefix())) {
            path = storageProperties.getConfig().getPrefix().concat(File.separator).concat(path);
        }

        return path;
    }

    /**
     * 根据文件名，生成路径
     *
     * @param fileName 文件名
     * @return 生成文件路径
     */
    public String getPath(String fileName) {
        return getPath().concat(File.separator).concat(getNewFileName(fileName));
    }

    public String getNewFileName(String fileName) {
        // 主文件名，不包含扩展名
        String prefix = FileNameUtil.getPrefix(fileName);
        // 文件扩展名
        String suffix = FileNameUtil.getSuffix(fileName);
        // 把当前时间，转换成毫秒
        long time = LocalTime.now().toNanoOfDay();
        return prefix
                .concat("_")
                .concat(String.valueOf(time))
                .concat(".")
                .concat(suffix);
    }

    /**
     * 下载文件
     *
     * @param url 文件地址
     * @param filename 文件名
     */
    public abstract ResponseEntity<byte[]> download(String url, String filename, AccessType accessType);
}
