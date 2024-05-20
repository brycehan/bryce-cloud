package com.brycehan.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统附件entity
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_attachment")
public class SysAttachment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件大小（单位字节）
     */
    private Long size;

    /**
     * 附件类型
     */
    private String type;

    /**
     * 附件名后缀
     */
    private String suffix;

    /**
     * 哈希码
     */
    private String hash;

    /**
     * 存储平台
     */
    private String platform;

}
