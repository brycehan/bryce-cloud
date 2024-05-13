package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.SysParamApi;
import com.brycehan.cloud.api.system.dto.SysParamApiDto;
import com.brycehan.cloud.api.system.vo.SysParamApiVo;
import com.brycehan.cloud.system.dto.SysParamDto;
import com.brycehan.cloud.system.service.SysParamService;
import com.brycehan.cloud.system.vo.SysParamVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class SysParamApiController implements SysParamApi {

    private final SysParamService sysParamService;

    @Override
    public void save(SysParamApiDto sysParamApiDto) {
        SysParamDto sysParam = new SysParamDto();
        BeanUtils.copyProperties(sysParamApiDto, sysParam);
        this.sysParamService.save(sysParam);
    }

    @Override
    public void update(SysParamApiDto sysParamApiDto) {
        SysParamDto sysParam = new SysParamDto();
        BeanUtils.copyProperties(sysParamApiDto, sysParam);
        this.sysParamService.update(sysParam);
    }

    @Override
    public Boolean exists(String paramKey) {
        return this.sysParamService.exists(paramKey);
    }

    @Override
    public SysParamApiVo getByParamKey(String paramKey) {
        SysParamVo sysParamVo = this.sysParamService.getByParamKey(paramKey);

        SysParamApiVo sysParamApiVo = new SysParamApiVo();
        BeanUtils.copyProperties(sysParamVo, sysParamApiVo);

        return sysParamApiVo;
    }

    @Override
    public String getString(String paramKey) {
        return this.sysParamService.getString(paramKey);
    }

    @Override
    public Boolean getBoolean(String paramKey) {
        return this.sysParamService.getBoolean(paramKey);
    }

}
