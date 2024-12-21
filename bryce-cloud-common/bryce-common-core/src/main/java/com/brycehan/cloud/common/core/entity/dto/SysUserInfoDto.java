package com.brycehan.cloud.common.core.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 2, max = 50)
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 手机号码
     */
    @NotNull
    @Length(max = 20)
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @Length(max = 50)
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别（M：男，F：女，N：未知）
     */
    @Schema(description = "性别（M：男，F：女，N：未知）")
    private GenderType gender;

}
