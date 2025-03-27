package com.brycehan.cloud.common.core.base;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户上下文
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Slf4j
@SuppressWarnings("unused")
public class LoginUserContext {

    private static final InheritableThreadLocal<String> userKeyHolder = new TransmittableThreadLocal<>();
    private static final InheritableThreadLocal<String> userDataHolder = new TransmittableThreadLocal<>();
    public static final TransmittableThreadLocal<Boolean> rememberMeHolder = new TransmittableThreadLocal<>();
    private static final InheritableThreadLocal<SourceClientType> sourceClientHolder = new TransmittableThreadLocal<>();

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户
     */
    public static LoginUser currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception ignored){
        }
        return null;
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static Long currentUserId() {
        LoginUser loginUser = currentUser();
        if(loginUser == null){
            return null;
        }

        return loginUser.getId();
    }

    /**
     * 获取当前登录用户的部门ID
     *
     * @return 当前登录用户的部门ID
     */
    public static Long currentDeptId() {
        LoginUser loginUser = currentUser();
        if(loginUser == null){
            return null;
        }

        return loginUser.getDeptId();
    }

    public static String currentOpenId() {
        LoginUser loginUser = currentUser();
        if(loginUser == null){
            return null;
        }

        return loginUser.getOpenId();
    }

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
    }

    /**
     * 获取用户标识
     *
     * @return 用户标识
     */
    public static String getUserKey() {
        return userKeyHolder.get();
    }

    /**
     * 设置用户标识
     *
     * @param userKey 用户标识
     */
    public static void setUserKey(String userKey) {
        userKeyHolder.set(userKey);
    }

    /**
     * 获取用户数据
     * @return 用户数据
     */
    public static String getUserData() {
        return userDataHolder.get();
    }

    /**
     * 设置用户数据
     *
     * @param userData 用户数据
     */
    public static void setUserData(String userData) {
        userDataHolder.set(userData);
    }

    /**
     * 获取客户端类型
     * @return 客户端类型
     */
    public static SourceClientType getSourceClient() {
        return sourceClientHolder.get();
    }

    /**
     * 设置客户端类型
     *
     * @param sourceClient 客户端类型
     */
    public static void setSourceClient(SourceClientType sourceClient) {
        sourceClientHolder.set(sourceClient);
    }
}
