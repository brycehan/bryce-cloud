package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统菜单权限标识Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统菜单权限标识Dto")
public class SysMenuAuthorityDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 权限标识
     */
    @NotBlank
    @Length(min = 5, max = 100)
    @Schema(description = "权限标识")
    private String authority;

}
