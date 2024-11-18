package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.validator.SaveGroup;
import com.brycehan.cloud.common.core.validator.UpdateGroup;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.convert.SysAttachmentConvert;
import com.brycehan.cloud.system.entity.dto.SysAttachmentDto;
import com.brycehan.cloud.system.entity.dto.SysAttachmentPageDto;
import com.brycehan.cloud.system.entity.po.SysAttachment;
import com.brycehan.cloud.system.entity.vo.SysAttachmentVo;
import com.brycehan.cloud.system.service.SysAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统附件API
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Tag(name = "系统附件")
@RequestMapping("/attachment")
@RestController
@RequiredArgsConstructor
public class SysAttachmentController {

    private final SysAttachmentService sysAttachmentService;

    /**
     * 保存系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统附件")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("hasAuthority('system:attachment:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysAttachmentDto sysAttachmentDto) {
        this.sysAttachmentService.save(sysAttachmentDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统附件")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("hasAuthority('system:attachment:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysAttachmentDto sysAttachmentDto) {
        this.sysAttachmentService.update(sysAttachmentDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统附件
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统附件")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('system:attachment:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysAttachmentService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统附件详情
     *
     * @param id 系统附件ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统附件详情")
    @PreAuthorize("hasAuthority('system:attachment:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysAttachmentVo> get(@Parameter(description = "系统附件ID", required = true) @PathVariable Long id) {
        SysAttachment sysAttachment = this.sysAttachmentService.getById(id);
        return ResponseResult.ok(SysAttachmentConvert.INSTANCE.convert(sysAttachment));
    }

    /**
     * 系统附件分页查询
     *
     * @param sysAttachmentPageDto 查询条件
     * @return 系统附件分页列表
     */
    @Operation(summary = "系统附件分页查询")
    @PreAuthorize("hasAuthority('system:attachment:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysAttachmentVo>> page(@Validated @RequestBody SysAttachmentPageDto sysAttachmentPageDto) {
        PageResult<SysAttachmentVo> page = this.sysAttachmentService.page(sysAttachmentPageDto);
        return ResponseResult.ok(page);
    }

}