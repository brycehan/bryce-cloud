package com.brycehan.cloud.monitor.controller;

import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.util.JsonUtils;
import com.brycehan.cloud.monitor.vo.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控API
 *
 * @since 2023/7/12
 * @author Bryce Han
 */
@Tag(name = "服务器监控", description = "server")
@RestController
@RequestMapping(path = "/monitor/server")
public class ServerController {

    /**
     * 服务器相关信息
     *
     * @return 响应结果
     */
    @Operation(summary = "服务器相关信息")
    @PreAuthorize("hasAuthority('monitor:server:info')")
    @GetMapping(path = "/info")
    public ResponseResult<Server> info() {
        return ResponseResult.ok(new Server());
    }

}
