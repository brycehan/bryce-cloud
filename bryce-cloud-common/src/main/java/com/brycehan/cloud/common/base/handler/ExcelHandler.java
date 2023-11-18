package com.brycehan.cloud.common.base.handler;

/**
 * Excel自字义字段处理器
 *
 * @since 2022/7/28
 * @author Bryce Han
 */
public interface ExcelHandler {

    /**
     * 字段值格式化处理
     *
     * @param value 单元格原始值
     * @param args  Excel注解args参数组
     * @return 处理后的值
     */
    Object format(Object value, String[] args);

}
