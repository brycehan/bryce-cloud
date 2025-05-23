package com.brycehan.cloud.common.core.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
public class SysUserAvatarDto extends BaseDto {

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @NotEmpty
    @Length(max = 200)
    private String avatar;

}
