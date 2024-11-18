package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BasePageDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统通知公告PageDto
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统通知公告PageDto")
public class SysNoticePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 公告类型（0：通知，1：公告）
     */
    @Schema(description = "公告类型（0：通知，1：公告）")
    private Integer type;

    /**
     * 状态（0：关闭，1：正常）
     */
    @Schema(description = "状态（0：关闭，1：正常）")
    private StatusType status;

}
