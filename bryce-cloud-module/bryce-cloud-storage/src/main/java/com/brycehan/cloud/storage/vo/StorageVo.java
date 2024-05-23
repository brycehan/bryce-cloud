package com.brycehan.cloud.storage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 存储 vo
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Data
@Tag(name = "存储 vo")
public class StorageVo {

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String name;

    /**
     * 文件地址
     */
    @Schema(description = "文件地址")
    private String url;

    /**
     * 文件大小（单位字节）
     */
    @Schema(description = "文件大小（单位字节）")
    private Long size;

    /**
     * 附件类型
     */
    @Schema(description = "附件类型")
    private String type;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    private String suffix;

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
}
