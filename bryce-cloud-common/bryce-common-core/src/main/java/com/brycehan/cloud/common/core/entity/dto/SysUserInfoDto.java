package com.brycehan.cloud.common.core.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 个人信息Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "个人信息Dto")
public class SysUserInfoDto extends BaseDto {

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
     * 性别（M：男，F：女，N：未知）
     */
    @Schema(description = "性别（M：男，F：女，N：未知）")
    private GenderType gender;

}
