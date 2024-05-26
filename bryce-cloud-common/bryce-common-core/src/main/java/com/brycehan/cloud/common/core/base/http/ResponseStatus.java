package com.brycehan.cloud.common.core.base.http;

/**
 * 响应状态
 *
 * @since 2022/2/25
 * @author Bryce Han
 */
public interface ResponseStatus {

    /**
     * 获取响应编码
     *
     * @return 响应编码
     */
    Integer code();

    /**
     * 获取响应值
     *
     * @return 响应值
     */
    String value();

    /**
     * 获取响应消息
     *
     * @return 响应消息
     */
    default String message() {
        return value();
    }

}
