package com.brycehan.cloud.system.vo;

import cn.hutool.core.date.DatePattern;
import com.brycehan.cloud.system.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统通知公告Vo
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Data
@Schema(description = "系统通知公告Vo")
public class SysNoticeVo implements Serializable, TransPojo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 内容
     */
    @JsonDeserialize(using = StringDeserializer.class)
    @Schema(description = "内容")
    private String content;

    /**
     * 公告类型（0：通知，1：公告）
     */
    @Schema(description = "公告类型（0：通知，1：公告）")
    private Integer type;

    /**
     * 状态（0：关闭，1：正常）
     */
    @Schema(description = "状态（0：关闭，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;


    /**
     * 创建者ID
     */
    @Trans(type = TransType.SIMPLE, target = SysUser.class, fields = "username", ref = "createdUsername")
    private Long createdUserId;

    /**
     * 创建者账号
     */
    private String createdUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}
