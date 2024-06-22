package com.brycehan.cloud.common.security.common;

import feign.Headers;
import feign.MethodMetadata;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * 自定义Spring MVC Contract，增强处理控制层类上的 Feign Headers 注解
 *
 * @author Bryce Han
 * @since 2024/6/22
 */
@Component
public class CustomSpringMvcContract extends SpringMvcContract {

    /**
     * 处理控制层类上的注解
     *
     * @param data 控制层类的方法的metadata
     * @param clz 控制层类
     */
    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        super.processAnnotationOnClass(data, clz);
        Headers headersAnnotation = findMergedAnnotation(clz, Headers.class);
        if (headersAnnotation != null) {
            data.template().headers(toMap(headersAnnotation.value()));
        }

    }

    /**
     * 将字符串数组转换为键值对应的映射关系。
     * 每个字符串表示一个头部信息，格式为"key: value"。该方法将解析这些字符串，
     * 并以键为头部信息的名称，值为头部信息的值集合的方式构建一个映射。
     * 如果同一个头部信息出现多次，其值将被收集到同一个集合中。
     *
     * @param input 字符串数组，每个元素代表一个头部信息。
     * @return 返回一个映射，其中键是头部信息的名称，值是对应头部信息的值集合。
     */
    private static Map<String, Collection<String>> toMap(String[] input) {
        Map<String, Collection<String>> result = new LinkedHashMap<>(input.length);
        for (String header : input) {
            int colon = header.indexOf(':');

            // 获取请求头名称
            String name = header.substring(0, colon);
            if (StringUtils.isBlank(name)) {
                continue;
            }
            name = name.trim();

            if (!result.containsKey(name)) {
                result.put(name, new ArrayList<>(1));
            }
            result.get(name).add(header.substring(colon + 1).trim());
        }
        return result;
    }
}
