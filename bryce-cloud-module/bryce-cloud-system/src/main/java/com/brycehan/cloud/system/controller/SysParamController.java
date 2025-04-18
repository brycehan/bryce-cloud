package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.convert.SysParamConvert;
import com.brycehan.cloud.system.entity.dto.SysParamDto;
import com.brycehan.cloud.system.entity.dto.SysParamKeyDto;
import com.brycehan.cloud.system.entity.dto.SysParamPageDto;
import com.brycehan.cloud.system.entity.po.SysParam;
import com.brycehan.cloud.system.entity.vo.SysParamVo;
import com.brycehan.cloud.system.service.SysParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数API
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Tag(name = "系统参数")
@RequestMapping("/param")
@RestController
@RequiredArgsConstructor
public class SysParamController {

    private final SysParamService sysParamService;

    /**
     * 保存系统参数
     *
     * @param sysParamDto 系统参数Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统参数")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:param:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysParamDto sysParamDto) {
        sysParamService.save(sysParamDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统参数")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:param:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysParamDto sysParamDto) {
        sysParamService.update(sysParamDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统参数
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统参数")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:param:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysParamService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统参数详情
     *
     * @param id 系统参数ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统参数详情")
    @PreAuthorize("@auth.hasAuthority('system:param:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysParamVo> get(@Parameter(description = "系统参数ID", required = true) @PathVariable Long id) {
        SysParam sysParam = sysParamService.getById(id);
        return ResponseResult.ok(SysParamConvert.INSTANCE.convert(sysParam));
    }

    /**
     * 系统参数分页查询
     *
     * @param sysParamPageDto 查询条件
     * @return 系统参数分页列表
     */
    @Operation(summary = "系统参数分页查询")
    @PreAuthorize("@auth.hasAuthority('system:param:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysParamVo>> page(@Validated @RequestBody SysParamPageDto sysParamPageDto) {
        PageResult<SysParamVo> page = sysParamService.page(sysParamPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统参数导出数据
     *
     * @param sysParamPageDto 查询条件
     */
    @Operation(summary = "系统参数导出")
    @PreAuthorize("@auth.hasAuthority('system:param:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysParamPageDto sysParamPageDto) {
        sysParamService.export(sysParamPageDto);
    }

    /**
     * 获取系统参数值
     *
     * @param paramKey 参数键
     * @return 参数值
     */
    @Operation(summary = "获取系统参数值")
    @GetMapping(path = "/get/{paramKey}")
    public ResponseResult<String> getValueByParamKey(@Parameter(description = "参数键", required = true) @PathVariable String paramKey) {
        String paramValue = sysParamService.getString(paramKey);
        return ResponseResult.ok(paramValue);
    }

    /**
     * 校验系统参数键名是否唯一
     *
     * @param sysParamKeyDto 系统参数键名Dto
     * @return 响应结果，是否唯一
     */
    @Operation(summary = "校验系统参数键名是否唯一（true：唯一，false：不唯一）")
    @GetMapping(path = "/checkParamKeyUnique")
    public ResponseResult<Boolean> checkParamKeyUnique(@Validated SysParamKeyDto sysParamKeyDto) {
        boolean checked = sysParamService.checkParamKeyUnique(sysParamKeyDto);
        return ResponseResult.ok(checked);
    }

}