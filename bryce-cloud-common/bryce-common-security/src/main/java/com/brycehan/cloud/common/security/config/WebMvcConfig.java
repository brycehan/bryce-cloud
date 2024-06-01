//package com.brycehan.cloud.common.security.config;
//
//import com.brycehan.cloud.common.security.common.interceptor.SecurityContextInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * WebMvc配置
// *
// * @author Bryce Han
// * @since 2024/5/26
// */
////@Configuration
//@RequiredArgsConstructor
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    // 排除路径
//    public static final String[] EXCLUDE_PATH = {"/login", "/logout"};
//
//    private final SecurityContextInterceptor securityContextInterceptor;
//
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(securityContextInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns(EXCLUDE_PATH)
////                .order(-1);
////    }
//
//}
