package com.brycehan.cloud.common.security.config;

import com.brycehan.cloud.common.security.jwt.JwtAccessDeniedHandler;
import com.brycehan.cloud.common.security.jwt.JwtAuthenticationEntryPoint;
import com.brycehan.cloud.common.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 过滤器配置
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class SecurityFilterConfig implements Ordered {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final AuthProperties authProperties;

    /**
     * httpSecurity 配置
     * <p>
     * hasIpAddress     如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问<br>
     * access           SpringEL表达式结果为true时可以访问<br>
     * rememberMe       允许通过remember-me登录的用户访问<br>
     * authenticated    用户登录后可以访问<br>
     * fullyAuthenticated   用户完全认证可以访问（非remember-me下的自动登录）
     * </p>
     *
     * @param http the {@link HttpSecurity} to modify
     * @return SecurityFilterChain实例
     * @throws Exception 如果发生错误时
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 授权请求
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(authProperties.getIgnoreUrls().getAll()).permitAll()
                        .requestMatchers(HttpMethod.GET, authProperties.getIgnoreUrls().getGet()).permitAll()
                        .requestMatchers(HttpMethod.POST, authProperties.getIgnoreUrls().getPost()).permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated())
                // 基于token，不需要session
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 添加 jwt 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                // 禁用退出自动配置接口
                .logout(AbstractHttpConfigurer::disable)
                // 禁用X-Frame-Options
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 禁用csrf，jwt不需要csrf开启
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * 使用BCrypt强哈希函数密码加密实现
     *
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
