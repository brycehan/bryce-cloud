package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.convert.SysLoginLogConvert;
import com.brycehan.cloud.system.entity.dto.SysLoginLogPageDto;
import com.brycehan.cloud.system.entity.po.SysLoginLog;
import com.brycehan.cloud.system.entity.vo.SysLoginLogVo;
import com.brycehan.cloud.system.service.SysLoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统登录日志API
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Tag(name = "系统登录日志")
@RequestMapping("/loginLog")
@RestController
@RequiredArgsConstructor
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    /**
     * 删除系统登录日志
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统登录日志")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:loginLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysLoginLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统登录日志详情
     *
     * @param id 系统登录日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录日志详情")
    @PreAuthorize("@auth.hasAuthority('system:loginLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysLoginLogVo> get(@Parameter(description = "系统登录日志ID", required = true) @PathVariable Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return ResponseResult.ok(SysLoginLogConvert.INSTANCE.convert(sysLoginLog));
    }

    /**
     * 系统登录日志分页查询
     *
     * @param sysLoginLogPageDto 查询条件
     * @return 系统登录日志分页列表
     */
    @Operation(summary = "系统登录日志分页查询")
    @PreAuthorize("@auth.hasAuthority('system:loginLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysLoginLogVo>> page(@Validated @RequestBody SysLoginLogPageDto sysLoginLogPageDto) {
        PageResult<SysLoginLogVo> page = sysLoginLogService.page(sysLoginLogPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统登录日志导出数据
     *
     * @param sysLoginLogPageDto 查询条件
     */
    @Operation(summary = "系统登录日志导出")
    @PreAuthorize("@auth.hasAuthority('system:loginLog:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysLoginLogPageDto sysLoginLogPageDto) {
        sysLoginLogService.export(sysLoginLogPageDto);
    }

    /**
     * 清空系统登录日志
     *
     * @return 响应结果
     */
    @Operation(summary = "清空系统登录日志")
    @OperateLog(type = OperatedType.CLEAN_DATA)
    @PreAuthorize("@auth.hasAuthority('system:loginLog:delete')")
    @DeleteMapping(path = "/clean")
    public ResponseResult<Void> clean() {
        sysLoginLogService.cleanLoginLog();
        return ResponseResult.ok();
    }

}