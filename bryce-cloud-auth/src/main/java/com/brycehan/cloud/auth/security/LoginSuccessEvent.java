package com.brycehan.cloud.auth.security;

import com.brycehan.cloud.common.base.LoginUser;
import org.springframework.context.ApplicationEvent;

/**
 * @author Bryce Han
 * @since 2024/5/12
 */
public class LoginSuccessEvent extends ApplicationEvent {

    public LoginSuccessEvent(LoginUser source) {
        super(source);
    }
}
