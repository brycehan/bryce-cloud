package com.brycehan.cloud.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统角色编码Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@Schema(description = "系统角色编码Dto")
public class SysRoleCodeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    @Size(min = 2, max = 30,  message = "角色编码长度在2-50个字符")
    private String code;

}
