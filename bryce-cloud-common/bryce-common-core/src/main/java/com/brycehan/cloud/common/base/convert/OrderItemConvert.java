package com.brycehan.cloud.common.base.convert;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.brycehan.cloud.common.base.entity.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 分页排序项转换器
 *
 * @since 2023/4/13
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemConvert {

    OrderItemConvert INSTANCE = Mappers.getMapper(OrderItemConvert.class);

    OrderItem convert(OrderItemDto orderItemDto);

    List<OrderItem> convert(List<OrderItemDto> orderItemDtos);

}
