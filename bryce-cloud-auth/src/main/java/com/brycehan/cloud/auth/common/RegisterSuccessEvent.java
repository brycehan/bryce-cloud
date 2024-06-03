package com.brycehan.cloud.auth.common;

import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import org.springframework.context.ApplicationEvent;

/**
 * @author Bryce Han
 * @since 2024/5/29
 */
public class RegisterSuccessEvent extends ApplicationEvent {

    public RegisterSuccessEvent(SysUserVo sysUserVo) {
        super(sysUserVo);
    }

    public SysUserVo getSysUserVo() {
        return (SysUserVo) super.getSource();
    }
}
