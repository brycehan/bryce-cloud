package com.brycehan.cloud.framework.security;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.framework.security.JwtTokenProvider;
import com.brycehan.cloud.framework.security.TokenUtils;
import com.brycehan.cloud.framework.security.context.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Jwt认证过滤器
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = TokenUtils.getAccessToken(request);
        // accessToken 为空，表示未登录
        if(StrUtil.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取登录用户
        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(accessToken);
        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 用户存在，自动刷新令牌
        this.jwtTokenProvider.autoRefreshToken(loginUser);

        // 设置认证信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.debug("set Authentication to security context for '{}', uri: {}", loginUser.getUsername(), request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
