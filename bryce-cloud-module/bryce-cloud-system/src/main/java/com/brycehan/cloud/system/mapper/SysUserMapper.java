package com.brycehan.cloud.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户Mapper接口
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
@Mapper
public interface SysUserMapper extends BryceBaseMapper<SysUser> {

    default SysUser getByUsername(String username) {
        return this.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    default SysUser getByPhone(String phone) {
        return this.selectOne(new QueryWrapper<SysUser>().eq("phone", phone));
    }

    List<SysUser> roleUserList(Map<String, Object> params);

    List<SysUser> list(Map<String, Object> params);

}
