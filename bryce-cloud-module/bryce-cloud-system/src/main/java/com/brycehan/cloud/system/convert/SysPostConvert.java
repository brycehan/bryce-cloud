package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysPostDto;
import com.brycehan.cloud.system.entity.SysPost;
import com.brycehan.cloud.system.vo.SysPostVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统岗位转换器
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysPostConvert {

    SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

    SysPost convert(SysPostDto sysPostDto);

    SysPostVo convert(SysPost sysPost);

    List<SysPostVo> convert(List<SysPost> sysPostList);

}