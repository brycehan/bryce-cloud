package com.brycehan.cloud.framework.security;

import com.brycehan.cloud.common.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证不通过后的处理
 *
 * @since 2022/5/7
 * @author Bryce Han
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("验证不通过，请求地址：{}，提示信息：{}", request.getRequestURI(), authException.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        response.getWriter().print(JsonUtils.writeValueAsString(ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED)));
    }

}