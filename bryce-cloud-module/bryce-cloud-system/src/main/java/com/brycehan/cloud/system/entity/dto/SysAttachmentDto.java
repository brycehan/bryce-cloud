package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统附件Dto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统附件Dto")
public class SysAttachmentDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 附件大小（单位字节）
     */
    @Schema(description = "附件大小（单位字节）")
    private Long size;

    /**
     * 附件类型
     */
    @Schema(description = "附件类型")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String suffix;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String hash;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String platform;

}
