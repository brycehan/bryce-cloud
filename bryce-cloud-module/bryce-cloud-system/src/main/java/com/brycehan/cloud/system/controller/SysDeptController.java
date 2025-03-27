package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.convert.SysDeptConvert;
import com.brycehan.cloud.system.entity.dto.SysDeptDto;
import com.brycehan.cloud.system.entity.po.SysDept;
import com.brycehan.cloud.system.entity.vo.SysDeptVo;
import com.brycehan.cloud.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统部门API
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Tag(name = "系统部门")
@RequestMapping("/dept")
@RestController
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 保存系统部门
     *
     * @param sysDeptDto 系统部门Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统部门")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:org:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        sysDeptService.save(sysDeptDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统部门
     *
     * @param sysDeptDto 系统部门Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统部门")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:org:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        sysDeptService.update(sysDeptDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统部门
     *
     * @param idsDto 系统部门删除Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统部门")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:org:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysDeptService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统部门详情
     *
     * @param id 系统部门ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统部门详情")
    @PreAuthorize("@auth.hasAuthority('system:org:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysDeptVo> get(@Parameter(description = "系统部门ID", required = true) @PathVariable Long id) {
        sysDeptService.checkDeptDataScope(id);
        SysDept sysDept = sysDeptService.getById(id);
        return ResponseResult.ok( SysDeptConvert.INSTANCE.convert(sysDept));
    }

    /**
     * 列表查询
     *
     * @param sysDeptDto 查询条件
     * @return 系统部门列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("@auth.hasAuthority('system:org:list')")
    @PostMapping(path = "/list")
    public ResponseResult<List<SysDeptVo>> list(@Validated @RequestBody SysDeptDto sysDeptDto) {
        List<SysDeptVo> list = sysDeptService.list(sysDeptDto);
        return ResponseResult.ok(list);
    }

}