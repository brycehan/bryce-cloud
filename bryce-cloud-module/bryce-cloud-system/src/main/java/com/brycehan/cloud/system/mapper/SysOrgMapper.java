package com.brycehan.cloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.cloud.system.entity.po.SysOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统机构Mapper接口
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    List<SysOrg> list(SysOrg sysOrg);

}
