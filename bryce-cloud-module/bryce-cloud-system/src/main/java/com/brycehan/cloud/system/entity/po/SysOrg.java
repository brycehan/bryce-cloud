package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统机构entity
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_org")
public class SysOrg extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 父机构ID
     */
    private Long parentId;

    /**
     * 祖级机构列表
     */
    private String ancestor;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

}
