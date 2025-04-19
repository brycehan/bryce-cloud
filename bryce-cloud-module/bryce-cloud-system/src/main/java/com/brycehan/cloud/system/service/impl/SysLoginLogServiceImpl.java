package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.entity.convert.SysLoginLogConvert;
import com.brycehan.cloud.system.entity.dto.SysLoginLogPageDto;
import com.brycehan.cloud.system.entity.po.SysLoginLog;
import com.brycehan.cloud.system.entity.vo.SysLoginLogVo;
import com.brycehan.cloud.system.mapper.SysLoginLogMapper;
import com.brycehan.cloud.system.service.SysLoginLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统登录日志服务实现
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public PageResult<SysLoginLogVo> page(SysLoginLogPageDto sysLoginLogPageDto) {
        IPage<SysLoginLog> page = baseMapper.selectPage(sysLoginLogPageDto.toPage(), getWrapper(sysLoginLogPageDto));
        return PageResult.of(SysLoginLogConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param sysLoginLogPageDto 系统登录日志分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysLoginLog> getWrapper(SysLoginLogPageDto sysLoginLogPageDto) {
        LambdaQueryWrapper<SysLoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysLoginLogPageDto.getStatus()), SysLoginLog::getStatus, sysLoginLogPageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysLoginLogPageDto.getUsername()), SysLoginLog::getUsername, sysLoginLogPageDto.getUsername());
        wrapper.like(StringUtils.isNotEmpty(sysLoginLogPageDto.getIp()), SysLoginLog::getIp, sysLoginLogPageDto.getIp());
        addTimeRangeCondition(wrapper, SysLoginLog::getAccessTime, sysLoginLogPageDto.getAccessTimeStart(), sysLoginLogPageDto.getAccessTimeEnd());

        return wrapper;
    }

    @Override
    public void export(SysLoginLogPageDto sysLoginLogPageDto) {
        List<SysLoginLog> sysLoginLogList = baseMapper.selectList(getWrapper(sysLoginLogPageDto));
        List<SysLoginLogVo> sysLoginLogVoList = SysLoginLogConvert.INSTANCE.convert(sysLoginLogList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysLoginLogVo.class, "系统登录日志_".concat(today), "系统登录日志", sysLoginLogVoList);
    }

    @Override
    public void cleanLoginLog() {
        baseMapper.cleanLoginLog();
    }

}
