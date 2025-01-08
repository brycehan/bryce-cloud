package com.brycehan.cloud.common.api.base;


import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.Field;

/**
 * 枚举转字符串转换器
 *
 * @author Bryce Han
 * @since 2025/1/8
 */
public class EnumToStringConverter implements Converter<Enum<?>, String> {

    @Override
    public String convert(Enum<?> source) {
        Class<?> enumClass = source.getClass();
        for (Field field : ReflectUtil.getFields(enumClass)) {
            JsonValue jsonValue = AnnotationUtils.findAnnotation(field, JsonValue.class);
            if (jsonValue != null) {
                field.setAccessible(true);
                try {
                    return String.valueOf(field.get(source));
                } catch (IllegalAccessException ignored) {
                }
            }
        }

        return source.name();
    }
}
