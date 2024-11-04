package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.convert.SysOperateLogConvert;
import com.brycehan.cloud.system.entity.dto.SysOperateLogDto;
import com.brycehan.cloud.system.entity.dto.SysOperateLogPageDto;
import com.brycehan.cloud.system.entity.po.SysOperateLog;
import com.brycehan.cloud.system.entity.vo.SysOperateLogVo;

/**
 * 系统操作日志服务
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
public interface SysOperateLogService extends BaseService<SysOperateLog> {

    /**
     * 添加系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    default void save(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        sysOperateLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysOperateLog);
    }

    /**
     * 更新系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    default void update(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        this.getBaseMapper().updateById(sysOperateLog);
    }

    /**
     * 查询系统操作日志详情
     *
     * @param id 系统操作日志ID
     * @return 系统操作日志VO
     */
    SysOperateLogVo get(Long id);

    /**
     * 系统操作日志分页查询
     *
     * @param sysOperateLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysOperateLogVo> page(SysOperateLogPageDto sysOperateLogPageDto);

    /**
     * 系统操作日志导出数据
     *
     * @param sysOperateLogPageDto 系统操作日志查询条件
     */
    void export(SysOperateLogPageDto sysOperateLogPageDto);

}
