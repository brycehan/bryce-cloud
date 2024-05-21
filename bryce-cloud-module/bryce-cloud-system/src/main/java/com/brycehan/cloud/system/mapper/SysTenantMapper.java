package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统租户Mapper接口
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Mapper
public interface SysTenantMapper extends BryceBaseMapper<SysTenant> {

}
