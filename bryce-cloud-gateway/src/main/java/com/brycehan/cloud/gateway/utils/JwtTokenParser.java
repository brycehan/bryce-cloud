package com.brycehan.cloud.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.cloud.common.core.constant.JwtConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Jwt令牌工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenParser {

    /**
     * jwt密钥
     */
    @Value("${bryce.auth.jwt.secret}")
    private String jwtSecret = "UZCiSM60eRJMOFA9mbiy";

    /**
     * 获取用户key
     *
     * @param claimMap 数据声明
     * @return 用户key
     */
    public static String getUserKey(Map<String, Claim> claimMap) {
        if (claimMap.get(JwtConstants.USER_KEY) == null) {
            return null;
        }

        return claimMap.get(JwtConstants.USER_KEY).asString();
    }

    /**
     * 获取用户数据
     *
     * @param claimMap 数据声明
     * @return 用户数据
     */
    public static String getUserData(Map<String, Claim> claimMap) {
        if (claimMap.get(JwtConstants.USER_DATA) == null) {
            return null;
        }

        return claimMap.get(JwtConstants.USER_DATA).asString();
    }

    /**
     * 校验token
     *
     * @param authToken 令牌
     * @return 校验令牌是否有效（true：有效，false：无效）
     */
    public DecodedJWT validateToken(String authToken) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(authToken);

    }

}
