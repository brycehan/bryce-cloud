package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BasePageDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 系统岗位PageDto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统岗位PageDto")
public class SysPostPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Length(max = 50)
    private String name;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @Length(max = 30)
    private String code;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

}
