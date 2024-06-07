package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.SysLoginLogApi;
import com.brycehan.cloud.api.system.entity.dto.SysLoginLogDto;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.system.service.SysLoginLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统登录日志Api实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统登录日志Api")
@RequestMapping(path = SysLoginLogApi.PATH)
@RestController
@RequiredArgsConstructor
public class SysLoginLogApiController implements SysLoginLogApi {

    private final SysLoginLogService sysLoginLogService;

    @Override
    public ResponseResult<Void> save(SysLoginLogDto sysLoginLogDto) {
        this.sysLoginLogService.save(sysLoginLogDto.getUsername(),
                sysLoginLogDto.isStatus(),
                sysLoginLogDto.getInfo());
        return ResponseResult.ok();
    }

}
