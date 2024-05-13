package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.base.dto.IdsDto;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.validator.SaveGroup;
import com.brycehan.cloud.common.validator.UpdateGroup;
import com.brycehan.cloud.framework.operatelog.annotation.OperateLog;
import com.brycehan.cloud.framework.operatelog.annotation.OperateType;
import com.brycehan.cloud.system.convert.SysOrgConvert;
import com.brycehan.cloud.system.dto.SysOrgDto;
import com.brycehan.cloud.system.dto.SysOrgPageDto;
import com.brycehan.cloud.system.entity.SysOrg;
import com.brycehan.cloud.system.service.SysOrgService;
import com.brycehan.cloud.system.vo.SysOrgVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统机构API
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Tag(name = "系统机构")
@RequestMapping("/org")
@RestController
@RequiredArgsConstructor
public class SysOrgController {

    private final SysOrgService sysOrgService;

    /**
     * 保存系统机构
     *
     * @param sysOrgDto 系统机构Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统机构")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:org:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysOrgDto sysOrgDto) {
        this.sysOrgService.save(sysOrgDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统机构
     *
     * @param sysOrgDto 系统机构Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统机构")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:org:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysOrgDto sysOrgDto) {
        this.sysOrgService.update(sysOrgDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统机构
     *
     * @param idsDto 系统机构删除Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统机构")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:org:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysOrgService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统机构详情
     *
     * @param id 系统机构ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统机构详情")
    @PreAuthorize("hasAuthority('system:org:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysOrgVo> get(@Parameter(description = "系统机构ID", required = true) @PathVariable Long id) {
        SysOrg sysOrg = this.sysOrgService.getById(id);
        SysOrgVo sysOrgVo = SysOrgConvert.INSTANCE.convert(sysOrg);

        // 获取上级机构名称
        if(sysOrg.getParentId() != 0) {
            SysOrg parent = this.sysOrgService.getById(sysOrg.getParentId());
            sysOrgVo.setParentName(parent.getName());
        } else {
            sysOrgVo.setParentName("主目类");
        }

        return ResponseResult.ok();
    }

    /**
     * 分页查询
     *
     * @param sysOrgPageDto 查询条件
     * @return 系统机构分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:org:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysOrgVo>> page(@Validated @RequestBody SysOrgPageDto sysOrgPageDto) {
        PageResult<SysOrgVo> page = this.sysOrgService.page(sysOrgPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 列表查询
     *
     * @param sysOrgDto 查询条件
     * @return 系统机构列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("hasAuthority('system:org:page')")
    @PostMapping(path = "/list")
    public ResponseResult<List<SysOrgVo>> list(@Validated @RequestBody SysOrgDto sysOrgDto) {
        List<SysOrgVo> list = this.sysOrgService.list(sysOrgDto);
        return ResponseResult.ok(list);
    }

}