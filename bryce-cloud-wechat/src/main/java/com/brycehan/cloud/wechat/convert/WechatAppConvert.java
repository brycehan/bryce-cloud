package com.brycehan.cloud.wechat.convert;

import com.brycehan.cloud.wechat.dto.WechatAppDto;
import com.brycehan.cloud.wechat.entity.WechatApp;
import com.brycehan.cloud.wechat.vo.WechatAppVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 微信应用转换器
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WechatAppConvert {

    WechatAppConvert INSTANCE = Mappers.getMapper(WechatAppConvert.class);

    WechatApp convert(WechatAppDto wechatAppDto);

    WechatAppVo convert(WechatApp wechatApp);

    List<WechatAppVo> convert(List<WechatApp> wechatAppList);

}