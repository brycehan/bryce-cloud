package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.DateTimeUtils;
import com.brycehan.cloud.common.core.util.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.system.entity.convert.SysLoginLogConvert;
import com.brycehan.cloud.system.entity.dto.SysLoginLogPageDto;
import com.brycehan.cloud.system.entity.po.SysLoginLog;
import com.brycehan.cloud.system.entity.vo.SysLoginLogVo;
import com.brycehan.cloud.system.mapper.SysLoginLogMapper;
import com.brycehan.cloud.system.service.SysLoginLogService;
import com.fhs.trans.service.impl.TransService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 系统登录日志服务实现
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    private final TransService transService;

    @Override
    public PageResult<SysLoginLogVo> page(SysLoginLogPageDto sysLoginLogPageDto) {
        IPage<SysLoginLog> page = this.baseMapper.selectPage(getPage(sysLoginLogPageDto), getWrapper(sysLoginLogPageDto));
        return new PageResult<>(page.getTotal(), SysLoginLogConvert.INSTANCE.convert(page.getRecords()));
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
        List<SysLoginLog> sysLoginLogList = this.baseMapper.selectList(getWrapper(sysLoginLogPageDto));
        List<SysLoginLogVo> sysLoginLogVoList = SysLoginLogConvert.INSTANCE.convert(sysLoginLogList);
        // 数据字典翻译
        this.transService.transBatch(sysLoginLogVoList);

        ExcelUtils.export(SysLoginLogVo.class, "系统登录日志_".concat(DateTimeUtils.today()), "系统登录日志", sysLoginLogVoList);
    }

    @Override
    public void save(String username, boolean status, Integer info) {
        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null) {
            return;
        }

        // 封装对象
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setId(IdGenerator.nextId());
        loginLog.setUsername(username);
        loginLog.setStatus(status);
        loginLog.setInfo(info);
        loginLog.setUserAgent(loginUser.getUserAgent());
        loginLog.setOs(loginUser.getOs());
        loginLog.setBrowser(loginUser.getBrowser());
        loginLog.setIp(loginUser.getLoginIp());
        loginLog.setLocation(loginUser.getLoginLocation());
        loginLog.setAccessTime(LocalDateTime.now());
        loginLog.setCreatedTime(LocalDateTime.now());

        // 保存数据
        this.baseMapper.insert(loginLog);
    }

}
