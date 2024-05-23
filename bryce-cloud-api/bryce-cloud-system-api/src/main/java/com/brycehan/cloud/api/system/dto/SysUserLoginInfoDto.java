package com.brycehan.cloud.api.system.dto;

import com.brycehan.cloud.common.core.validator.SaveGroup;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户登录信息Dto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@Schema(description = "系统用户登录信息Dto")
public class SysUserLoginInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @Size(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

}
