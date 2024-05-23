package com.brycehan.cloud.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.cloud.common.core.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统字典数据entity
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_dict_data")
public class SysDictData extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private Long dictTypeId;

    /**
     * 标签属性
     */
    private String labelClass;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

}
