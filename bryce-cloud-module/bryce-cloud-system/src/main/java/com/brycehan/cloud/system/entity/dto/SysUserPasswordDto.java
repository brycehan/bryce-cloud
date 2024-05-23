package com.brycehan.cloud.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户密码 Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@Schema(description = "系统用户密码Dto")
public class SysUserPasswordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @Schema(description = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String password;

    /**
     * 新密码
     */
    @Schema(description = "新密码（长度为6-30位）")
    @Length(min = 6, max = 30, message = "新密码长度为 6-30 位")
    private String newPassword;

}
