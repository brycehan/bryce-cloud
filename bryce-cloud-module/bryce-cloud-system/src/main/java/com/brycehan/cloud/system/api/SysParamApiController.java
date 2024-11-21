package com.brycehan.cloud.system.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.cloud.api.system.api.SysParamApi;
import com.brycehan.cloud.api.system.entity.dto.SysParamDto;
import com.brycehan.cloud.api.system.entity.vo.SysParamVo;
import com.brycehan.cloud.common.core.base.response.HttpResponseStatus;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.system.entity.po.SysParam;
import com.brycehan.cloud.system.service.SysParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数Api
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统参数Api")
@RequestMapping(path = SysParamApi.PATH)
@RestController
@RequiredArgsConstructor
public class SysParamApiController implements SysParamApi {

    private final SysParamService sysParamService;

    /**
     * 添加系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @Override
    @Operation(summary = "添加系统参数")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Void> save(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        this.sysParamService.save(sysParam);
        return ResponseResult.ok();
    }

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    @Override
    @Operation(summary = "更新系统参数")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Void> update(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        if (StringUtils.isNotEmpty(sysParam.getParamKey())) {
            LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysParam::getId);
            queryWrapper.eq(SysParam::getParamKey, sysParam.getParamKey());
            SysParam param = this.sysParamService.getOne(queryWrapper, false);
            if (param != null) {
                sysParam.setId(param.getId());
            }
        }

        if (sysParamDto.getId() == null) {
            return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST);
        }

        this.sysParamService.updateById(sysParam);

        return ResponseResult.ok();
    }

    /**
     * 判断 paramKey 是否存在
     *
     * @param paramKey 参数key
     *
     * @return paramKey 是否存在
     */
    @Override
    @Operation(summary = "判断 paramKey 是否存在")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Boolean> exists(String paramKey) {
        boolean exists = this.sysParamService.exists(paramKey);
        return ResponseResult.ok(exists);
    }

    /**
     * 获取参数对象
     *
     * @param paramKey 参数key
     * @return 参数对象
     */
    @Override
    @Operation(summary = "获取参数对象")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<SysParamVo> getByParamKey(String paramKey) {
        com.brycehan.cloud.system.entity.vo.SysParamVo sysParamVo = this.sysParamService.getByParamKey(paramKey);

        SysParamVo sysParamApiVo = new SysParamVo();
        BeanUtils.copyProperties(sysParamVo, sysParamApiVo);

        return ResponseResult.ok(sysParamApiVo);
    }

    /**
     * 根据paramKey，查询字符串类型的参数值
     *
     * @param paramKey 参数key
     * @return 参数值
     */
    @Override
    @Operation(summary = "根据paramKey，查询字符串类型的参数值")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<String> getString(String paramKey) {
        String string = this.sysParamService.getString(paramKey);
        return ResponseResult.ok(string);
    }

    /**
     * 根据paramKey，查询boolean类型的参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    @Override
    @Operation(summary = "根据paramKey，查询boolean类型的参数值")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Boolean> getBoolean(String paramKey) {
        boolean flag = this.sysParamService.getBoolean(paramKey);
        return ResponseResult.ok(flag);
    }

}
