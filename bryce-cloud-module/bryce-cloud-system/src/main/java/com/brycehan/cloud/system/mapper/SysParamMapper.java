package com.brycehan.cloud.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统参数Mapper接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper
@SuppressWarnings("all")
public interface SysParamMapper extends BryceBaseMapper<SysParam> {

    default boolean exists(String paramKey) {
        return exists(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey));
    }

    default SysParam selectOne(String paramKey) {
        return selectOne(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey), false);
    }

}
