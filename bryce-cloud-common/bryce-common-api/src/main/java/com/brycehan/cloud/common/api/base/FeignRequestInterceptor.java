package com.brycehan.cloud.common.api.base;

import com.brycehan.cloud.common.core.base.LoginUserContextHolder;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.constant.TokenConstants;
import com.brycehan.cloud.common.core.enums.YesNoType;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * Feign 请求配置
 *
 * @author Bryce Han
 * @since 2023/11/19
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

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

                    // 原请求存在时，添加请求头 user_key、user_data、X-Source-Client
                    if (name.equalsIgnoreCase(JwtConstants.USER_KEY)
                            || name.equalsIgnoreCase(JwtConstants.USER_DATA)
                            || name.equalsIgnoreCase(TokenConstants.SOURCE_CLIENT_HEADER)) {
                        requestTemplate.header(name, values);
                    }
                }
                // Feign内部调用添加请求头 X-Inner-Call
                requestTemplate.header(DataConstants.INNER_CALL, YesNoType.YES.getValue());
            }
        } else {
            // Async 异步调用时处理请求头
            if (StringUtils.hasText(LoginUserContextHolder.getUserKey())) {
                requestTemplate.header(JwtConstants.USER_KEY, LoginUserContextHolder.getUserKey());
            }
            if (StringUtils.hasText(LoginUserContextHolder.getUserData())) {
                requestTemplate.header(JwtConstants.USER_DATA, LoginUserContextHolder.getUserData());
            }
            if (LoginUserContextHolder.getSourceClient() != null) {
                requestTemplate.header(TokenConstants.SOURCE_CLIENT_HEADER, LoginUserContextHolder.getSourceClient().getValue());
            }
            requestTemplate.header(DataConstants.INNER_CALL, YesNoType.YES.getValue());
        }
    }

}
