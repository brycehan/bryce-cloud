package com.brycehan.cloud.common.core.util;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 字符串工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
public class PasswordUtils {

    private static PasswordEncoder passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);

    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
