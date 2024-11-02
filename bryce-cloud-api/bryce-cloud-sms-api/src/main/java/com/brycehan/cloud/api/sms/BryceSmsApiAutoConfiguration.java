package com.brycehan.cloud.api.sms;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * Bryce短信Api自动配置
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@ComponentScan
@EnableFeignClients(basePackages = "com.brycehan.cloud.api.sms.client")
public class BryceSmsApiAutoConfiguration {

}
