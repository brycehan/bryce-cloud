package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.entity.BasePageDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.enums.VisibleType;
import com.brycehan.cloud.system.common.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 系统菜单PageDto
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单PageDto")
public class SysMenuPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Length(max = 50)
    private String name;

    /**
     * 类型（C：目录，M：菜单，B：按钮）
     */
    @Schema(description = "类型（C：目录，M：菜单，B：按钮）")
    private MenuType type;

    /**
     * 可见性类型
     */
    @Schema(description = "可见性类型")
    private VisibleType visible;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

}
