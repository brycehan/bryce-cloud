package com.brycehan.cloud.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Bryce应用配置
 *
 * @since 2022/9/19
 * @author Bryce Han
 */
@Data
@Component
@ConfigurationProperties(prefix = "bryce.application")
public class ApplicationProperties {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    public static String getAvatarPath() {
        return "/profile".concat("/avatar");
    }
}
