package com.brycehan.cloud.common.core.entity;

import com.brycehan.cloud.common.core.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @since 2023/4/10
 * @author Bryce Han
 */
@Data
@AllArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private long total;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private List<T> list;

    /**
     * 转换为JSON字符串
     * @return JSON字符串
     */
    public String toJson(){
        return JsonUtils.writeValueAsString(this);
    }

}
