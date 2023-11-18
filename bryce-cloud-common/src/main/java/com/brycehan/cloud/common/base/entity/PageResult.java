package com.brycehan.cloud.common.base.entity;

import com.brycehan.cloud.common.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果数据
 *
 * @since 2023/4/10
 * @author Bryce Han
 */
@Schema(description = "分页结果数据")
@Data
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "总记录数")
    private long total;

    @Schema(description = "列表数据")
    private List<T> list;

    public String toString(){
        return JsonUtils.writeValueAsString(this);
    }

}
