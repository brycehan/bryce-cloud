package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.dto.SysLoginLogPageDto;
import com.brycehan.cloud.system.entity.po.SysLoginLog;
import com.brycehan.cloud.system.entity.vo.SysLoginLogVo;

/**
 * 系统登录日志服务
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {

    /**
     * 系统登录日志分页查询
     *
     * @param sysLoginLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysLoginLogVo> page(SysLoginLogPageDto sysLoginLogPageDto);

    /**
     * 系统登录日志导出数据
     *
     * @param sysLoginLogPageDto 系统登录日志查询条件
     */
    void export(SysLoginLogPageDto sysLoginLogPageDto);

    /**
     * 清空登录日志
     */
    void cleanLoginLog();

}
