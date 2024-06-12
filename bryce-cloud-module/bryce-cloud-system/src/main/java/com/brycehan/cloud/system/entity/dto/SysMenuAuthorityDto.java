package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @Schema(description = "权限标识")
    @NotBlank(message = "权限标识不能为空")
    @Size(min = 5, max = 100, message = "权限标识长度在2-100个字符")
    private String authority;

}
