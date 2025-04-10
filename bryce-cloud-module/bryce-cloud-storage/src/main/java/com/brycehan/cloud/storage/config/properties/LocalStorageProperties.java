package com.brycehan.cloud.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * 本地存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.local")
public class LocalStorageProperties {
    /**
     * 本地存储目录
     */
    private String directory;

    /**
     * 资源起始路径
     */
    private String prefix = "storage";

    /**
     * 获取访问路径前缀
     *
     * @return 访问路径前缀
     */
    public final String getAccessPrefix() {
        return directory.concat(File.separator).concat(prefix);
    }

    /**
     * 根据存储路径获取访问路径
     *
     * @param path 访问路径
     * @return 保存路径
     */
    public final String getAccessPath(String path) {
        return getAccessPrefix().concat(File.separator).concat(path);
    }
}
