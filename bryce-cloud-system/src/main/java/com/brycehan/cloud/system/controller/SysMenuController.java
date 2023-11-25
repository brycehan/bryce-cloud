package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.base.dto.IdsDto;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.validator.SaveGroup;
import com.brycehan.cloud.common.validator.UpdateGroup;
import com.brycehan.cloud.framework.operatelog.annotation.OperateLog;
import com.brycehan.cloud.framework.operatelog.annotation.OperateType;
import com.brycehan.cloud.system.convert.SysMenuConvert;
import com.brycehan.cloud.system.dto.SysMenuDto;
import com.brycehan.cloud.system.dto.SysMenuPageDto;
import com.brycehan.cloud.system.entity.SysMenu;
import com.brycehan.cloud.system.service.SysMenuService;
import com.brycehan.cloud.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统菜单API
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Tag(name = "系统菜单", description = "sysMenu")
@RequestMapping("/system/menu")
@RestController
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 保存系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统菜单")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:menu:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.save(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统菜单")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:menu:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.update(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统菜单
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统菜单")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        // 判断是否有子菜单或按钮
        Long count = this.sysMenuService.getSubMenuCount(idsDto.getIds());
        if (count > 0) {
            return ResponseResult.error("请先删除子菜单");
        }

        this.sysMenuService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统菜单详情
     *
     * @param id 系统菜单ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统菜单详情")
    @PreAuthorize("hasAuthority('system:menu:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysMenuVo> get(@Parameter(description = "系统菜单ID", required = true) @PathVariable Long id) {
        SysMenu sysMenu = this.sysMenuService.getById(id);
        SysMenuVo sysMenuVo = SysMenuConvert.INSTANCE.convert(sysMenu);

        // 获取上级菜单名称
        if (sysMenu.getParentId() != 0) {
            SysMenu parent = this.sysMenuService.getById(sysMenu.getParentId());
            sysMenuVo.setParentName(parent.getName());
        }

        return ResponseResult.ok(sysMenuVo);
    }

    /**
     * 分页查询
     *
     * @param sysMenuPageDto 查询条件
     * @return 系统菜单分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:menu:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysMenuVo>> page(@Validated @RequestBody SysMenuPageDto sysMenuPageDto) {
        PageResult<SysMenuVo> page = this.sysMenuService.page(sysMenuPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统菜单导出数据
     *
     * @param sysMenuPageDto 查询条件
     */
    @Operation(summary = "系统菜单导出")
    @PreAuthorize("hasAuthority('system:menu:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysMenuPageDto sysMenuPageDto) {
        this.sysMenuService.export(sysMenuPageDto);
    }

    /**
     * 列表查询
     *
     * @param sysMenuDto 查询条件
     * @return 系统菜单列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("hasAuthority('system:menu:page')")
    @PostMapping(path = "/list")
    public ResponseResult<List<SysMenuVo>> list(@Validated @RequestBody SysMenuDto sysMenuDto) {
        List<SysMenuVo> list = this.sysMenuService.list(sysMenuDto);
        return ResponseResult.ok(list);
    }
}

