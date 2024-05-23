package com.brycehan.cloud.api.system.dto;

import com.brycehan.cloud.common.core.validator.SaveGroup;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统登录日志Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统登录日志Dto")
public class SysLoginLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 登录状态
     */
    @Schema(description = "登录状态")
    private boolean status;

    /**
     * 操作信息
     */
    @Schema(description = "操作信息")
    private Integer info;

}
