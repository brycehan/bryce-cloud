package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.base.entity.PageResult;
import com.brycehan.cloud.common.core.base.id.IdGenerator;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.convert.SysOrgConvert;
import com.brycehan.cloud.system.dto.SysOrgDto;
import com.brycehan.cloud.system.dto.SysOrgPageDto;
import com.brycehan.cloud.system.entity.SysOrg;
import com.brycehan.cloud.system.vo.SysOrgVo;

import java.util.List;

/**
 * 系统机构服务
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
public interface SysOrgService extends BaseService<SysOrg> {

    /**
     * 添加系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    default void save(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        sysOrg.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysOrg);
    }

    /**
     * 更新系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    default void update(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        this.getBaseMapper().updateById(sysOrg);
    }

    /**
     * 系统机构分页查询
     *
     * @param sysOrgPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysOrgVo> page(SysOrgPageDto sysOrgPageDto);

    /**
     * 列表查询
     *
     * @param sysOrgDto 查询参数
     * @return 机构列表
     */
    List<SysOrgVo> list(SysOrgDto sysOrgDto);

    /**
     * 根据机构ID，获取子机构ID列表（包含本机构ID）
     *
     * @param id 机构ID
     * @return 子机构ID列表
     */
    List<Long> getSubOrgIds(Long id);

}
