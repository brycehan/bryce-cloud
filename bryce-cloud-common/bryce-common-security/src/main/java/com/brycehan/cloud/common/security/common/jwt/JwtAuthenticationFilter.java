package com.brycehan.cloud.common.security.common.jwt;

import cn.hutool.core.util.StrUtil;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import com.brycehan.cloud.common.core.util.JsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

        log.info("请求URI，{}", request.getRequestURI());

        String userKey = request.getHeader(JwtConstants.USER_KEY);
        String userData = request.getHeader(JwtConstants.USER_DATA);

        // userKey、userData为空，表示未登录
        if(StrUtil.isEmpty(userKey) && StrUtil.isEmpty(userData)) {
            filterChain.doFilter(request, response);
            return;
        }

        LoginUser loginUser = null;

        // 获取登录用户
        if(StringUtils.isNotEmpty(userKey)) {
            loginUser = this.jwtTokenProvider.loadLoginUser(userKey);
        } else if (StringUtils.isNotEmpty(userData)) {
            loginUser = JsonUtils.readValue(userData, LoginUser.class);
        }

        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 用户存在，自动刷新令牌
        this.jwtTokenProvider.autoRefreshToken(loginUser);

        // 设置认证信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.debug("将认证信息设置到安全上下文中，username：{}', uri: {}", loginUser.getUsername(), request.getRequestURI());

        filterChain.doFilter(request, response);
    }

}