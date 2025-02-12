package com.brycehan.cloud.storage.config;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.storage.config.properties.LocalStorageProperties;
import com.brycehan.cloud.storage.config.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 资源配置
 *
 * @since 2022/11/4
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LocalStorageProperties.class)
@ConditionalOnProperty(prefix = "bryce.storage", value = "enabled")
public class LocalResourceConfig implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    private final LocalStorageProperties localStorageProperties;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 公共文件URL访问前缀
        String urlPublicPrefix;
        // 公共文件file协议访问前缀
        String accessPublicPrefix;

        String publicPrefix = storageProperties.getConfig().getPublicPrefix();
        // 获取公共文件访问前缀
        if (StrUtil.isNotEmpty(publicPrefix)) {
            urlPublicPrefix = "/".concat(publicPrefix);
            accessPublicPrefix = File.separator.concat(publicPrefix).concat(File.separator);
        } else {
            urlPublicPrefix = "";
            accessPublicPrefix = File.separator;
        }

        // 本地文件访问路径映射
        registry.addResourceHandler(urlPublicPrefix.concat("/**"))
                .addResourceLocations("file:".concat(localStorageProperties.getAccessPrefix()).concat(accessPublicPrefix));
    }
}
