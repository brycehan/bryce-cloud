package com.brycehan.cloud.common.core.response;

import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.util.StringFormatUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 响应结果
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Slf4j
@Data
@Schema(description = "响应结果")
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应编码（200表示成功，其它值表示失败）
     */
    @Schema(description = "响应编码（200表示成功，其它值表示失败）")
    private Integer code = HttpResponseStatus.HTTP_OK.code();

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message = HttpResponseStatus.HTTP_OK.message();

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 响应时间
     */
    @Schema(description = "响应时间")
    private LocalDateTime time;

    /**
     * 响应成功
     *
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok() {
        return ok(null);
    }

    /**
     * 响应成功
     *
     * @param data 响应数据
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok(T data) {
        return ok(data, HttpResponseStatus.HTTP_OK.message());
    }

    /**
     * 响应成功
     *
     * @param data   响应数据
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok(T data, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setData(data);
        responseResult.setMessage(message);
        responseResult.setTime(LocalDateTime.now());
        return responseResult;
    }

    /**
     * 响应失败
     *
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error() {
        return error(HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }

    /**
     * 响应失败
     *
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(String message) {
        return error(HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), message);
    }

    /**
     * 响应失败
     *
     * @param code    响应编码
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setTime(LocalDateTime.now());
        return responseResult;
    }

    /**
     * 响应失败
     *
     * @param responseStatus 响应状态
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(ResponseStatus responseStatus) {
        return error(responseStatus.code(), responseStatus.message());
    }

    /**
     * 响应失败
     *
     * @param responseStatus 响应状态
     * @param params         参数
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(ResponseStatus responseStatus, String ...params) {
        return error(responseStatus.code(), StringFormatUtils.format(responseStatus.message(), params));
    }

    /**
     * 响应失败
     *
     * @param serverException 服务异常
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(ServerException serverException) {
        return error(serverException.getCode(), serverException.getMessage());
    }

    /**
     * 响应失败
     *
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> fallback(String message) {
        return error(600, message);
    }

    /**
     * 是否成功
     *
     * @param responseResult 响应结果
     * @return 是否成功
     */
    public static boolean isSuccess(ResponseResult<?> responseResult) {
        return responseResult != null && Objects.equals(responseResult.getCode(), HttpResponseStatus.HTTP_OK.code());
    }

}
