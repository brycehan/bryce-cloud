package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.common.core.base.vo.MenuVo;
import com.brycehan.cloud.system.dto.SysMenuDto;
import com.brycehan.cloud.system.entity.SysMenu;
import com.brycehan.cloud.system.vo.SysMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统菜单转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenu convert(SysMenuDto sysMenuDto);

    SysMenuVo convert(SysMenu sysMenu);

    List<SysMenuVo> convert(List<SysMenu> sysMenuList);

    @Mapping(source = "name", target = "name")
    List<MenuVo> convertMenu(List<SysMenuVo> sysMenuVoList);
}
