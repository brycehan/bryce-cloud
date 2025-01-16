package com.brycehan.cloud.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * 监控中心Web安全配置
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AdminServerProperties adminServer;

    public WebSecurityConfig(AdminServerProperties adminServerProperties) {
        adminServer = adminServerProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminServer.path("/"));

        http
                // 授权请求
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                        // 配置静态资源和登录页放行
                                adminServer.path("/assets/**"),
                                adminServer.path("/login"),
                                adminServer.path("/actuator/info"),
                                adminServer.path("/actuator/health")).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated())
                // 配置登录和退出
                .formLogin(configurer -> configurer.loginPage(adminServer.path("/login")).successHandler(successHandler))
                .logout(configurer -> configurer.logoutUrl(adminServer.path("/logout")).permitAll())
                // 开启Http Basic支持，admin-client注册时需要使用
                .httpBasic(Customizer.withDefaults())
                // 禁用X-Frame-Options
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(configurer -> configurer
                        // 开启基于Cookie的csrf保护
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        // 忽略这些路径的csrf保护，以便admin-client注册
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher(adminServer.path("/instances"), POST.toString()),
                                new AntPathRequestMatcher(adminServer.path("/instances/*"), DELETE.toString()),
                                new AntPathRequestMatcher(adminServer.path("/actuator/**"))
                                ))
                .rememberMe(configurer -> configurer.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));

        return http.build();
    }

}
