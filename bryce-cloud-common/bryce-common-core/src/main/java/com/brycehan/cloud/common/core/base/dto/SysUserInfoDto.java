package com.brycehan.cloud.common.core.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 个人信息Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "个人信息Dto")
public class SysUserInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @NotNull
    @Size(min = 2, max = 50)
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 手机号码
     */
    @NotNull
    @Size(max = 20)
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @Size(max = 50)
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别（M：男, F：女）
     */
    @Schema(description = "性别（M：男, F：女）")
    @Pattern(regexp = "^[MF]$", message = "性别值只能是M或F")
    private String gender;

}
