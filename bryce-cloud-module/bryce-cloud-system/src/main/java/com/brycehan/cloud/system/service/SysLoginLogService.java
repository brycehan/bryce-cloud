package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.enums.LoginOperateType;
import com.brycehan.cloud.common.core.enums.OperationStatusType;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.dto.SysLoginLogDto;
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
     * 添加系统登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    void save(SysLoginLogDto sysLoginLogDto);

    /**
     * 更新系统登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    void update(SysLoginLogDto sysLoginLogDto);

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
     * 保存登录日志
     *
     * @param username 用户账号
     * @param status   登录状态
     * @param info     操作信息
     */
    void save(String username, OperationStatusType status, LoginOperateType info);
}
