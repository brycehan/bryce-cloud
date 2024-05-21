package com.brycehan.cloud.common.core.base.dto;

import com.brycehan.cloud.common.core.constant.UserConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "注册Dto")
public class RegisterDto {

    /**
     * 账号
     */
    @NotEmpty
    @Size(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH)
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    @Size(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH)
    @Schema(description = "密码")
    private String password;

    /**
     * key
     */
    @Size(max = 36)
    @Schema(description = "key")
    private String key;

    /**
     * 验证码
     */
    @Size(max = 8)
    @Schema(description = "验证码")
    private String code;

}
