package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysUserPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户岗位关系Mapper接口
 *
 * @since 2023/09/30
 * @author Bryce Han
 */
@Mapper
public interface SysUserPostMapper extends BryceBaseMapper<SysUserPost> {

}
