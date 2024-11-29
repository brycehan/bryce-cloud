package com.brycehan.cloud.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.brycehan.cloud.common.core.enums.EnumTypeDescConverter;
import com.brycehan.cloud.common.core.enums.GenderType;
import com.brycehan.cloud.common.core.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户Vo
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "系统用户Vo")
public class SysUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @ColumnWidth(14)
    @Schema(description = "账号")
    @ExcelProperty(value = "账号")
    private String username;

    /**
     * 姓名
     */
    @ColumnWidth(14)
    @Schema(description = "姓名")
    @ExcelProperty(value = "姓名")
    private String nickname;

    /**
     * 性别（M：男, F：女）
     */
    @Schema(description = "性别（M：男, F：女）")
    @ExcelProperty(value = "性别", converter = EnumTypeDescConverter.class)
    private GenderType gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Integer type;

    /**
     * 手机号码
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "手机号码")
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "邮箱")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 机构ID
     */
    @ExcelProperty(value = "机构ID")
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String orgName;

    /**
     * 是否是超级管理员
     */
    @Schema(description = "是否是超级管理员")
    private Boolean superAdmin;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @ExcelProperty(value = "状态", converter = EnumTypeDescConverter.class)
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 角色IDs
     */
    @Schema(description = "角色IDs")
    private List<Long> roleIds;

    /**
     * 岗位IDs
     */
    @Schema(description = "岗位ID")
    private List<Long> postIds;

}
