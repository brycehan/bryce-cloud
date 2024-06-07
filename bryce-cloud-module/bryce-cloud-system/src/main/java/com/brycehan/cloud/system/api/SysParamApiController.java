package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.entity.dto.SysParamDto;
import com.brycehan.cloud.api.system.entity.vo.SysParamApiVo;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.system.entity.po.SysParam;
import com.brycehan.cloud.system.entity.vo.SysParamVo;
import com.brycehan.cloud.system.service.SysParamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统参数Api")
@RestController
@RequiredArgsConstructor
public class SysParamApiController implements SysParamApi {

    private final SysParamService sysParamService;

    @Override
    public ResponseResult<Void> save(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        this.sysParamService.save(sysParam);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult<Void> update(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        if (sysParam.getId() == null) {
            if (StringUtils.isNotEmpty(sysParam.getParamKey())) {
                SysParamVo sysParamVo = this.sysParamService.getByParamKey(sysParam.getParamKey());
                sysParam.setId(sysParamVo.getId());
            }
        }

        if (sysParam.getId() == null) {
            return ResponseResult.error("参数ID不能为空");
        }

        this.sysParamService.updateById(sysParam);

        return ResponseResult.ok();
    }

    @Override
    public ResponseResult<Boolean> exists(String paramKey) {
        boolean exists = this.sysParamService.exists(paramKey);
        return ResponseResult.ok(exists);
    }

    @Override
    public ResponseResult<SysParamApiVo> getByParamKey(String paramKey) {
        SysParamVo sysParamVo = this.sysParamService.getByParamKey(paramKey);

        SysParamApiVo sysParamApiVo = new SysParamApiVo();
        BeanUtils.copyProperties(sysParamVo, sysParamApiVo);

        return ResponseResult.ok(sysParamApiVo);
    }

    @Override
    public ResponseResult<String> getString(String paramKey) {
        String string = this.sysParamService.getString(paramKey);
        return ResponseResult.ok(string);
    }

    @Override
    public ResponseResult<Boolean> getBoolean(String paramKey) {
        boolean flag = this.sysParamService.getBoolean(paramKey);
        return ResponseResult.ok(flag);
    }

}
