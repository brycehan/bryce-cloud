package com.brycehan.cloud.quartz.controller;

import com.brycehan.cloud.common.base.dto.IdsDto;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.framework.operatelog.annotation.OperateLog;
import com.brycehan.cloud.framework.operatelog.annotation.OperateType;
import com.brycehan.cloud.quartz.convert.QuartzJobLogConvert;
import com.brycehan.cloud.quartz.dto.QuartzJobLogPageDto;
import com.brycehan.cloud.quartz.entity.QuartzJobLog;
import com.brycehan.cloud.quartz.service.QuartzJobLogService;
import com.brycehan.cloud.quartz.vo.QuartzJobLogVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * quartz 定时任务调度日志API
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Tag(name = "quartz 定时任务调度日志", description = "quartzJobLog")
@RequestMapping("/quartz/jobLog")
@RestController
@RequiredArgsConstructor
public class QuartzJobLogController {

    private final QuartzJobLogService quartzJobLogService;

    /**
     * 删除quartz 定时任务调度日志
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除quartz 定时任务调度日志")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('quartz:jobLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.quartzJobLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询quartz 定时任务调度日志详情
     *
     * @param id quartz 定时任务调度日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询quartz 定时任务调度日志详情")
    @PreAuthorize("hasAuthority('quartz:jobLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<QuartzJobLogVo> get(@Parameter(description = "quartz 定时任务调度日志ID", required = true) @PathVariable Long id) {
        QuartzJobLog quartzJobLog = this.quartzJobLogService.getById(id);
        return ResponseResult.ok(QuartzJobLogConvert.INSTANCE.convert(quartzJobLog));
    }

    /**
     * 分页查询
     *
     * @param quartzJobLogPageDto 查询条件
     * @return quartz 定时任务调度日志分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('quartz:jobLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<QuartzJobLogVo>> page(@Validated @RequestBody QuartzJobLogPageDto quartzJobLogPageDto) {
        PageResult<QuartzJobLogVo> page = this.quartzJobLogService.page(quartzJobLogPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * quartz 定时任务调度日志导出数据
     *
     * @param quartzJobLogPageDto 查询条件
     */
    @Operation(summary = "quartz 定时任务调度日志导出")
    @PreAuthorize("hasAuthority('quartz:jobLog:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody QuartzJobLogPageDto quartzJobLogPageDto) {
        this.quartzJobLogService.export(quartzJobLogPageDto);
    }

}