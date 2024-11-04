package com.brycehan.cloud.common.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.cloud.common.core.util.JsonUtils;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity 基类
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 删除标识（null：正常，非null：删除）
     */
    @TableLogic
    private LocalDateTime deleted;

    /**
     * 创建者ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改者ID
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 转换为JSON字符串
     *
     * @return JSON字符串
     */
    @SuppressWarnings("unused")
    public String toJson(){
        return JsonUtils.writeValueAsString(this);
    }

}
