package com.brycehan.cloud.api.system.entity.dto;

import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.LoginStatus;
import com.brycehan.cloud.common.core.enums.OperateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统登录日志Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统登录日志Dto")
public class SysLoginLogDto extends BaseDto {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 登录状态
     */
    @Schema(description = "登录状态")
    private OperateStatus status;

    /**
     * 操作信息
     */
    @Schema(description = "操作信息")
    private LoginStatus info;

}
