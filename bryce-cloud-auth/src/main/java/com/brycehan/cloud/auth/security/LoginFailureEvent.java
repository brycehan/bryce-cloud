package com.brycehan.cloud.auth.security;

import com.brycehan.cloud.common.core.base.LoginUser;
import org.springframework.context.ApplicationEvent;

/**
 * @author Bryce Han
 * @since 2024/5/12
 */
public class LoginFailureEvent extends ApplicationEvent {

    public LoginFailureEvent(LoginUser source) {
        super(source);
    }
}
