package com.brycehan.cloud.api.system.entity.dto;

import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统用户登录信息Dto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户登录信息Dto")
public class SysUserLoginInfoDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @Length(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

}
