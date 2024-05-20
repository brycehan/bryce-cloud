package com.brycehan.cloud.system.dto;

import com.brycehan.cloud.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 地区编码 PageDto
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Data
@Schema(description = "地区编码 PageDto")
@EqualsAndHashCode(callSuper = true)
public class SysAreaCodePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 255)
    private String name;

}
