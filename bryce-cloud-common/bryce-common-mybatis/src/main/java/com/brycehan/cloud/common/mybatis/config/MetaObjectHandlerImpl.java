package com.brycehan.cloud.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.security.context.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

/**
 * Mybatis Plus字段填充处理器
 *
 * @since 2022/5/12
 * @author Bryce Han
 */
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser != null) {
            // 创建者ID
            strictInsertFill(metaObject, "createdUserId", Long.class, loginUser.getId());
            // 创建者所属机构
            strictInsertFill(metaObject, "orgId", Long.class, loginUser.getOrgId());
        }

        // 创建时间
        strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        // 版本号
        strictInsertFill(metaObject, "version", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新者ID
        Long userId = LoginUserContext.currentUserId();
        if (userId != null) {
            strictUpdateFill(metaObject, "updatedUserId", Long.class, userId);
        }

        // 更新时间
        strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("bryce1@3"));
    }
}
