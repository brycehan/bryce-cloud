package com.brycehan.cloud.system.mapper;

import com.brycehan.cloud.common.mybatis.mapper.BryceBaseMapper;
import com.brycehan.cloud.system.entity.po.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统通知公告Mapper接口
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Mapper
public interface SysNoticeMapper extends BryceBaseMapper<SysNotice> {

}
