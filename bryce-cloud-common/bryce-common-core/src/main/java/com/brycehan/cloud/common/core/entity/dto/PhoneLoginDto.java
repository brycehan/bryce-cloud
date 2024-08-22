package com.brycehan.cloud.common.core.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.util.RegexPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手机号登录Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "手机号登录Dto")
public class PhoneLoginDto extends BaseDto {

    /**
     * 手机号
     */
    @NotBlank
    @Pattern(regexp = RegexPatterns.PHONE_REGEX, message = "手机号格式错误")
    @Schema(description = "手机号")
    private String phone;

    /**
     * 验证码
     */
    @NotBlank
    @Pattern(regexp = RegexPatterns.VERIFY_CODE_REGEX, message = "验证码格式错误")
    @Schema(description = "验证码")
    private String code;

}
