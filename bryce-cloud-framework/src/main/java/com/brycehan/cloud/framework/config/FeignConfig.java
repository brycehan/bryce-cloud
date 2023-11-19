package com.brycehan.cloud.framework.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * feign 配置
 *
 * @author Bryce Han
 * @since 2023/11/19
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取到请求头
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if(headerNames != null) {
                // 循环设置请求头，进行转发
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);

                    // 跳过 content-length
                    if(name.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
                        continue;
                    }

                    requestTemplate.header(name, values);
                }
            }
        }

    }
}
