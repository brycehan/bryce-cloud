package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.base.validator.SaveGroup;
import com.brycehan.cloud.common.core.base.validator.UpdateGroup;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.dto.SysNoticeDto;
import com.brycehan.cloud.system.entity.dto.SysNoticePageDto;
import com.brycehan.cloud.system.entity.vo.SysNoticeVo;
import com.brycehan.cloud.system.service.SysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统通知公告API
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Tag(name = "系统通知公告")
@RequestMapping("/notice")
@RestController
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    /**
     * 保存系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统通知公告")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:notice:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysNoticeDto sysNoticeDto) {
        sysNoticeService.save(sysNoticeDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统通知公告")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:notice:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysNoticeDto sysNoticeDto) {
        sysNoticeService.update(sysNoticeDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统通知公告
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统通知公告")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:notice:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysNoticeService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统通知公告详情
     *
     * @param id 系统通知公告ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统通知公告详情")
    @PreAuthorize("@auth.hasAuthority('system:notice:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysNoticeVo> get(@Parameter(description = "系统通知公告ID", required = true) @PathVariable Long id) {
        SysNoticeVo sysNoticeVo = sysNoticeService.get(id);
        return ResponseResult.ok(sysNoticeVo);
    }

    /**
     * 系统通知公告分页查询
     *
     * @param sysNoticePageDto 查询条件
     * @return 系统通知公告分页列表
     */
    @Operation(summary = "系统通知公告分页查询")
    @PreAuthorize("@auth.hasAuthority('system:notice:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysNoticeVo>> page(@Validated @RequestBody SysNoticePageDto sysNoticePageDto) {
        PageResult<SysNoticeVo> page = sysNoticeService.page(sysNoticePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统通知公告导出数据
     *
     * @param sysNoticePageDto 查询条件
     */
    @Operation(summary = "系统通知公告导出")
    @PreAuthorize("@auth.hasAuthority('system:notice:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysNoticePageDto sysNoticePageDto) {
        sysNoticeService.export(sysNoticePageDto);
    }

}