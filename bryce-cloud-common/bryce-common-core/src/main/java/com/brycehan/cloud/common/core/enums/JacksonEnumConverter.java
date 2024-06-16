package com.brycehan.cloud.common.core.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Jackson枚举转换器
 *
 * @author Bryce Han
 * @since 2024/6/16
 */
@Slf4j
@Component
public class JacksonEnumConverter implements GenericConverter {

    private final ObjectMapper objectMapper;
    private final Set<ConvertiblePair> convertiblePairs;

    public JacksonEnumConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.convertiblePairs = Set.of(
                new ConvertiblePair(String.class, Enum.class),
                new ConvertiblePair(Enum.class, String.class)
        );
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, @NotNull TypeDescriptor sourceType, @NotNull TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        try {
            return objectMapper.readValue("\"" + source + "\"", targetType.getType());
        } catch (Exception e) {
            log.error("convert，枚举转换失败，目标类型：{}，参数：{}，错误消息：{}", targetType.getName(), source, e.getMessage());
            throw new RuntimeException(targetType.getName() + "枚举转换失败" + source);
        }
    }

}