package com.brycehan.cloud.system.controller;

import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import com.brycehan.cloud.system.entity.dto.SysUserOnlinePageDto;
import com.brycehan.cloud.system.entity.vo.SysUserOnlineVo;
import com.brycehan.cloud.system.service.SysUserOnlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户API
 *
 * @since 2023/10/12
 * @author Bryce Han
 */
@Tag(name = "在线用户")
@RestController
@RequestMapping(path = "/onlineUser")
@RequiredArgsConstructor
public class SysUserOnlineController {

    private final SysUserOnlineService sysUserOnlineService;

    /**
     * 在线用户分页查询
     *
     * @param sysUserOnlinePageDto 查询条件
     * @return 在线用户分页列表
     */
    @Operation(summary = "在线用户分页查询")
    @PreAuthorize("@auth.hasAuthority('monitor:onlineUser:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysUserOnlineVo>> page(@Validated @RequestBody SysUserOnlinePageDto sysUserOnlinePageDto) {
        if (StringUtils.isNotBlank(sysUserOnlinePageDto.getUsername()) || StringUtils.isNotBlank(sysUserOnlinePageDto.getLoginIp())) {
            PageResult<SysUserOnlineVo> pageResult = sysUserOnlineService.pageByUsernameAndLoginIp(sysUserOnlinePageDto);
            return ResponseResult.ok(pageResult);
        } else {
            PageResult<SysUserOnlineVo> pageResult = sysUserOnlineService.page(sysUserOnlinePageDto);
            return ResponseResult.ok(pageResult);
        }


    }

    /**
     * 强制退出
     *
     * @param userKey 会话存储Key
     * @return 响应结果
     */
    @Operation(summary = "强制退出")
    @OperateLog(type = OperatedType.FORCE_QUIT)
    @PreAuthorize("@auth.hasAuthority('monitor:onlineUser:delete')")
    @DeleteMapping(path = "/{userKey}")
    public ResponseResult<Void> delete(@PathVariable String userKey) {
        sysUserOnlineService.deleteLoginUser(userKey);
        return ResponseResult.ok();
    }

}
