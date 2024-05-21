package com.brycehan.cloud.common.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 权限工具类
 *
 * @author Bryce Han
 * @since 2024/5/11
 */
public class SecurityUtils {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 对给定的密码进行编码。
     *
     * @param password 需要进行编码的原始密码。
     * @return 编码后的密码字符串。该字符串采用BCrypt加密算法进行编码，提高了密码的安全性。
     */
    public static String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * 验证给定的密码是否与编码后的密码匹配。
     * @param password 需要验证的密码。
     * @param encodedPassword 加密后的密码
     * @return boolean 返回验证结果，如果输入的密码与编码后的密码匹配，则返回true；否则返回false。
     */
    public static boolean matches(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

}
