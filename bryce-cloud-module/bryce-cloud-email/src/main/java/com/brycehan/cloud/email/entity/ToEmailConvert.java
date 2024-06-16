package com.brycehan.cloud.email.entity;

import com.brycehan.cloud.api.email.entity.ToMail;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 发送邮件转换器
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToEmailConvert {

    ToEmailConvert INSTANCE = Mappers.getMapper(ToEmailConvert.class);

    ToMail convert(ToVerifyCodeEmailDto toVerifyCodeEmailDto);

}