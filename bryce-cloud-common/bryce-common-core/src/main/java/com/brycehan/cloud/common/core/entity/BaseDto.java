package com.brycehan.cloud.common.core.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
