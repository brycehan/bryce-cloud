package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.module.system.SysParamApi;
import com.brycehan.cloud.system.service.SysParamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统参数 Api 实现", description = "sysParam")
@RestController
@RequiredArgsConstructor
public class SysParamApiController implements SysParamApi {

    private final SysParamService sysParamService;

    @Override
    public boolean getBoolean(String paramKey) {
        return this.sysParamService.getBoolean(paramKey);
    }
}
