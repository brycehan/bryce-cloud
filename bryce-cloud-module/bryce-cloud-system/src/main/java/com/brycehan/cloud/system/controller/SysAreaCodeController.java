package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.dto.IdsDto;
import com.brycehan.cloud.common.core.base.entity.PageResult;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.system.entity.convert.SysAreaCodeConvert;
import com.brycehan.cloud.system.entity.dto.SysAreaCodeDto;
import com.brycehan.cloud.system.entity.dto.SysAreaCodePageDto;
import com.brycehan.cloud.system.entity.po.SysAreaCode;
import com.brycehan.cloud.system.service.SysAreaCodeService;
import com.brycehan.cloud.system.entity.vo.SysAreaCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 地区编码API
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Tag(name = "地区编码", description = "sysAreaCode")
@RequestMapping("/system/areaCode")
@RestController
@RequiredArgsConstructor
public class SysAreaCodeController {

    private final SysAreaCodeService sysAreaCodeService;

    /**
     * 更新地区编码
     *
     * @param sysAreaCodeDto 地区编码Dto
     * @return 响应结果
     */
    @Operation(summary = "更新地区编码")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:areaCode:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysAreaCodeDto sysAreaCodeDto) {
        this.sysAreaCodeService.update(sysAreaCodeDto);
        return ResponseResult.ok();
    }

    /**
     * 删除地区编码
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除地区编码")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:areaCode:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysAreaCodeService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询地区编码详情
     *
     * @param id 地区编码ID
     * @return 响应结果
     */
    @Operation(summary = "查询地区编码详情")
    @PreAuthorize("hasAuthority('system:areaCode:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysAreaCodeVo> get(@Parameter(description = "地区编码ID", required = true) @PathVariable Long id) {
        SysAreaCode sysAreaCode = this.sysAreaCodeService.getById(id);
        return ResponseResult.ok(SysAreaCodeConvert.INSTANCE.convert(sysAreaCode));
    }

    /**
     * 分页查询
     *
     * @param sysAreaCodePageDto 查询条件
     * @return 地区编码分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:areaCode:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysAreaCodeVo>> page(@Validated @RequestBody SysAreaCodePageDto sysAreaCodePageDto) {
        PageResult<SysAreaCodeVo> page = this.sysAreaCodeService.page(sysAreaCodePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 地区编码导出数据
     *
     * @param sysAreaCodePageDto 查询条件
     */
    @Operation(summary = "地区编码导出")
    @PreAuthorize("hasAuthority('system:areaCode:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysAreaCodePageDto sysAreaCodePageDto) {
        this.sysAreaCodeService.export(sysAreaCodePageDto);
    }

}