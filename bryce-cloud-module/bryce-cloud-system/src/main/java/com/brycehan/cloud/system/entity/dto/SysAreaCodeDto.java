package com.brycehan.cloud.system.entity.dto;

import com.brycehan.cloud.common.core.base.entity.BaseDto;
import com.brycehan.cloud.common.core.validator.SaveGroup;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地区编码Dto
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "地区编码Dto")
public class SysAreaCodeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Integer id;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Integer parentId;

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer deep;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 拼音前缀
     */
    @Schema(description = "拼音前缀")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String pinyinPrefix;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String pinyin;

    /**
     * 扩展ID
     */
    @Schema(description = "扩展ID")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String extId;

    /**
     * 扩展名称
     */
    @Schema(description = "扩展名称")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String extName;

}
