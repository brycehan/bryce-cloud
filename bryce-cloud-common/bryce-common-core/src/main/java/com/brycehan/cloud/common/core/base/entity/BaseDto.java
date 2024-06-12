package com.brycehan.cloud.common.core.base.entity;

import com.brycehan.cloud.common.core.util.JsonUtils;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础分页 DTO 数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 转换为JSON字符串
     * @return JSON字符串
     */
    public String toJson(){
        return JsonUtils.writeValueAsString(this);
    }

}
