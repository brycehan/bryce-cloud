package com.brycehan.cloud.storage.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.storage.config.StorageType;
import com.brycehan.cloud.storage.config.properties.StorageProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

/**
 * 存储服务
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
public abstract class StorageService {

    protected StorageProperties storageProperties;

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @param accessType 访问类型
     * @return http资源地址
     */
    @SuppressWarnings("unused")
    public String upload(byte[] data, String path, AccessType accessType) {
        return upload(new ByteArrayInputStream(data), path, accessType);
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
     *
     * @param accessType 访问类型
     * @return 生成的路径
     */
    public String getPath(AccessType accessType) {
        // 文件路径
        String path = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        // 如果有前缀，则也带上
        path = storageProperties.getConfig().getAccessPrefix(accessType).concat(path);

        return path;
    }

    /**
     * 根据文件名，生成路径
     *
     * @param accessType 访问类型
     * @param fileName 文件名
     * @return 生成文件路径
     */
    public String getPath(String fileName, AccessType accessType) {
        return getPath(accessType).concat(File.separator).concat(getNewFileName(fileName));
    }

    /**
     * 生成新文件名
     *
     * @param fileName 文件名
     * @return 新文件名
     */
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
     * 获取当前存储平台
     *
     * @return 当前存储类型
     */
    public String getPlatform() {
        return Optional.ofNullable(storageProperties.getConfig())
                .map(StorageProperties.Config::getType)
                .map(StorageType::name).orElseThrow(() -> new RuntimeException("请开启存储配置"));
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     */
    public abstract byte[] download(String path);

    /**
     * 获取文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    @SuppressWarnings("unused")
    public String getFilenameByPath(String path) {
        String filename = StrUtil.subAfter(path, "/", true).split("_")[0];
        if (StrUtil.isBlank(filename)) {
            filename = "download";
        }
        return filename;
    }

    /**
     * 获取文件字节数组
     *
     * @param inputStream 文件输入流
     * @return 文件字节数组
     */
    public byte[] getByteArrayByInputStream(InputStream inputStream) {
        // 获取文件的字节数组
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IoUtil.copy(inputStream, outputStream, 10240);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ServerException("读取文件出错：", e);
        } finally {
            IoUtil.close(inputStream);
        }
    }
}
