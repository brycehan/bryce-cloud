package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.entity.BaseEntity;
import com.brycehan.cloud.common.core.enums.NoticeType;
import com.brycehan.cloud.common.core.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统通知公告entity
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_notice")
public class SysNotice extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 公告类型（0：通知，1：公告）
     */
    private NoticeType type;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 备注
     */
    private String remark;

}
