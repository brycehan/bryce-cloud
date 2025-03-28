package com.brycehan.cloud.system.entity.vo;

import com.brycehan.cloud.common.core.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统附件Vo
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@Schema(description = "系统附件Vo")
public class SysAttachmentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    private String name;

    /**
     * 附件路径
     */
    @Schema(description = "附件路径")
    private String path;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
    private String url;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    private String suffix;

    /**
     * 访问类型
     */
    @Schema(description = "访问类型")
    private AccessType accessType;

    /**
     * 附件大小（单位字节）
     */
    @Schema(description = "附件大小（单位字节）")
    private Long size;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    private String hash;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    private String platform;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
