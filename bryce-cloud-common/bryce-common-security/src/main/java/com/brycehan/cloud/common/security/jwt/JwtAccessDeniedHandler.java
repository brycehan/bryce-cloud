package com.brycehan.cloud.common.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Jwt访问拒绝处理器
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("访问拒绝，{}", accessDeniedException.getMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
    }

}
