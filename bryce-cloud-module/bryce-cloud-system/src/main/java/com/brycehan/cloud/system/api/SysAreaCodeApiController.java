package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.SysAreaCodeApi;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.system.service.SysAreaCodeService;
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
@Tag(name = "系统参数Api实现")
@RestController
@RequiredArgsConstructor
public class SysAreaCodeApiController implements SysAreaCodeApi {

    private final SysAreaCodeService sysAreaCodeService;

    @Override
    public ResponseResult<String> getExtNameByCode(String areaCode) {
        String extNameByCode = this.sysAreaCodeService.getExtNameByCode(areaCode);
        return ResponseResult.ok(extNameByCode);
    }

    @Override
    public ResponseResult<String> getFullLocation(String areaCode) {
        String fullLocation = this.sysAreaCodeService.getFullLocation(areaCode);
        return ResponseResult.ok(fullLocation);
    }
}
