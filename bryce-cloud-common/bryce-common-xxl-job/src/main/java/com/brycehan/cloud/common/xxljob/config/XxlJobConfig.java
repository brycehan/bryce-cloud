package com.brycehan.cloud.common.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * XXL-JOB 配置
 *
 * @author Bryce Han
 * @since 2024/3/22
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "xxl.job.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfig {

    private static final Logger log = LoggerFactory.getLogger(XxlJobConfig.class);

    /**
     * 配置 xxl-job 执行器
     *
     * @param properties xxl-job配置属性
     * @return XxlJobSpringExecutor
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties) {
        log.info("xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();

        XxlJobProperties.Admin admin = properties.getAdmin();
        XxlJobProperties.Executor executor = properties.getExecutor();

        if (admin != null) {
            xxlJobSpringExecutor.setAdminAddresses(admin.getAddresses());
        }

        if (executor != null) {
            xxlJobSpringExecutor.setAppname(executor.getAppname());
            xxlJobSpringExecutor.setAddress(executor.getAddress());
            xxlJobSpringExecutor.setIp(executor.getIp());
            xxlJobSpringExecutor.setPort(executor.getPort());
            xxlJobSpringExecutor.setLogPath(executor.getLogPath());
            xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());
        }

        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());

        return xxlJobSpringExecutor;
    }

}
