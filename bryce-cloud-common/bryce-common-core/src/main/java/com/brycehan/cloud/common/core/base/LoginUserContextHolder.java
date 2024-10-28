package com.brycehan.cloud.common.core.base;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户 Holder
 *
 * @author Bryce Han
 * @since 2024/6/20
 */
public class LoginUserContextHolder {

    private static final InheritableThreadLocal<LoginUser> CONTEXT_HOLDER = new TransmittableThreadLocal<>();
    private static final InheritableThreadLocal<String> USER_KEY_HOLDER = new TransmittableThreadLocal<>();
    private static final InheritableThreadLocal<String> USER_DATA_HOLDER = new TransmittableThreadLocal<>();
    private static final InheritableThreadLocal<String> SOURCE_CLIENT_HOLDER = new TransmittableThreadLocal<>();


    /**
     * 设置登录用户
     *
     * @param loginUser 登录用户
     */
    public static void setContext(LoginUser loginUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        CONTEXT_HOLDER.set(loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getContext() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 获取用户标识
     *
     * @return 用户标识
     */
    public static String getUserKey() {
        return USER_KEY_HOLDER.get();
    }

    /**
     * 设置用户标识
     *
     * @param userKey 用户标识
     */
    public static void setUserKey(String userKey) {
        USER_KEY_HOLDER.set(userKey);
    }

    /**
     * 获取用户数据
     * @return 用户数据
     */
    public static String getUserData() {
        return USER_DATA_HOLDER.get();
    }

    /**
     * 设置用户数据
     *
     * @param userData 用户数据
     */
    public static void setUserData(String userData) {
        USER_DATA_HOLDER.set(userData);
    }

    /**
     * 获取客户端类型
     * @return 客户端类型
     */
    public static String getSourceClient() {
        return SOURCE_CLIENT_HOLDER.get();
    }

    /**
     * 设置客户端类型
     *
     * @param sourceClient 客户端类型
     */
    public static void setSourceClient(String sourceClient) {
        SOURCE_CLIENT_HOLDER.set(sourceClient);
    }

}
