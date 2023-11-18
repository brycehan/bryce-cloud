package com.brycehan.cloud.common.util;

import com.brycehan.cloud.common.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Sql注入工具类
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
public class SqlInjectUtils {

    /**
     * Sql注入过滤非法字符
     *
     * @param param
     * @return
     */
    public static String filter(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }

        // 去掉'|"|;|\字符
        param = RegExUtils.replaceAll(param, "\'", "");
        param = RegExUtils.replaceAll(param, "\"", "");
        param = RegExUtils.replaceAll(param, ";", "");
        param = RegExUtils.replaceAll(param, "\\", "");

        // 转换成小写
        param = param.toLowerCase();

        // 非法字符
        String[] keywords = {"master", "declare", "create", "drop", "alter", "insert", "delete", "truncate", "update", "select"};

        String finalParam = param;
        Arrays.stream(keywords).forEach(keyword -> {
            if (finalParam.contains(keyword)) {
                throw BusinessException.responseStatus(HttpResponseStatus.HTTP_PARAM_CONTAINS_ILLEGAL_CHAR);
            }
        });

        return param;
    }

}
