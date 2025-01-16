package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.SysAreaCodeApi;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.system.service.SysAreaCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统区域编码Api
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统区域编码Api")
@RequestMapping(path = SysAreaCodeApi.PATH)
@RestController
@RequiredArgsConstructor
public class SysAreaCodeApiController implements SysAreaCodeApi {

    private final SysAreaCodeService sysAreaCodeService;

    /**
     * 根据地区编码获取扩展名称
     *
     * @param areaCode 地区编码
     * @return 扩展名称
     */
    @Override
    @Operation(summary = "根据地区编码获取扩展名称")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<String> getExtNameByCode(String areaCode) {
        String extNameByCode = sysAreaCodeService.getExtNameByCode(areaCode);
        return ResponseResult.ok(extNameByCode);
    }

    /**
     * 获取地区位置
     *
     * @param areaCode 地区编码
     * @return 地区位置
     */
    @Override
    @Operation(summary = "获取地区位置")
    @PreAuthorize("@auth.hasInnerCall()")
    public ResponseResult<String> getFullLocation(String areaCode) {
        String fullLocation = sysAreaCodeService.getFullLocation(areaCode);
        return ResponseResult.ok(fullLocation);
    }
}
