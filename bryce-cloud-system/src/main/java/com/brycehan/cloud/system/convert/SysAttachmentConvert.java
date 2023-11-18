package com.brycehan.cloud.system.convert;

import com.brycehan.cloud.system.dto.SysAttachmentDto;
import com.brycehan.cloud.system.entity.SysAttachment;
import com.brycehan.cloud.system.vo.SysAttachmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统附件转换器
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Mapper
public interface SysAttachmentConvert {

    SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

    SysAttachment convert(SysAttachmentDto sysAttachmentDto);

    SysAttachmentVo convert(SysAttachment sysAttachment);

    List<SysAttachmentVo> convert(List<SysAttachment> sysAttachmentList);

}