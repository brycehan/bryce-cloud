package com.brycehan.cloud.common.core.base.dto;

import com.brycehan.cloud.common.core.constant.UserConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账号登录Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "账号登录Dto")
public class AccountLoginDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @NotNull
    @Size(min = 2, max = 50)
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @NotNull
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
