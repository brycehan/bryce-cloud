package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.framework.mybatis.service.BaseService;
import com.brycehan.cloud.system.convert.SysTenantConvert;
import com.brycehan.cloud.system.dto.SysTenantDto;
import com.brycehan.cloud.system.dto.SysTenantPageDto;
import com.brycehan.cloud.system.entity.SysTenant;
import com.brycehan.cloud.system.vo.SysTenantVo;

/**
 * 系统租户服务
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
public interface SysTenantService extends BaseService<SysTenant> {

    /**
     * 添加系统租户
     *
     * @param sysTenantDto 系统租户Dto
     */
    default void save(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = SysTenantConvert.INSTANCE.convert(sysTenantDto);
        sysTenant.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysTenant);
    }

    /**
     * 更新系统租户
     *
     * @param sysTenantDto 系统租户Dto
     */
    default void update(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = SysTenantConvert.INSTANCE.convert(sysTenantDto);
        this.getBaseMapper().updateById(sysTenant);
    }

    /**
     * 系统租户分页查询
     *
     * @param sysTenantPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysTenantVo> page(SysTenantPageDto sysTenantPageDto);

    /**
     * 系统租户导出数据
     *
     * @param sysTenantPageDto 系统租户查询条件
     */
    void export(SysTenantPageDto sysTenantPageDto);

}
