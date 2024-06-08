package com.brycehan.cloud.api.system.fallback;

import com.brycehan.cloud.api.system.api.SysUserApi;
import com.brycehan.cloud.api.system.entity.dto.SysUserDto;
import com.brycehan.cloud.api.system.entity.dto.SysUserLoginInfoDto;
import com.brycehan.cloud.api.system.entity.vo.SysUserVo;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class SysUserApiFallbackImpl implements FallbackFactory<SysUserApi> {

    @Override
    public SysUserApi create(Throwable cause) {
        log.error("系统服务调用失败，{}", cause.getMessage());

        return new SysUserApi() {
            @Override
            public ResponseResult<LoginUser> loadUserByUsername(String username) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<LoginUser> loadUserByPhone(String phone) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<LoginUser> loadUserById(Long id) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<SysUserVo> registerUser(SysUserDto sysUserDto) {
                return ResponseResult.fallback("系统服务调用失败");
            }

            @Override
            public ResponseResult<Boolean> updateLoginInfo(SysUserLoginInfoDto sysUserLoginInfoDto) {
                return ResponseResult.fallback("系统服务调用失败");
            }
        };
    }
}
