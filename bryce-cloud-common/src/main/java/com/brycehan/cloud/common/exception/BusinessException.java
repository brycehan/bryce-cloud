package com.brycehan.cloud.common.exception;

import com.brycehan.cloud.common.base.http.ResponseStatus;
import com.brycehan.cloud.common.util.MessageUtils;
import com.brycehan.cloud.common.util.StringFormatUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;

/**
 * 业务异常
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Slf4j
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 异常消息键
     */
    private String messageKey;

    /**
     * 异常消息键对应的参数
     */
    private Object[] messageArgs;

    protected BusinessException(String message) {
        super(message);
        code = 500;
    }

    protected BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    protected BusinessException(ResponseStatus responseStatus) {
        super(responseStatus.message());
        this.code = responseStatus.code();
    }

    protected BusinessException(ResponseStatus responseStatus, String... params) {
        super(StringFormatUtils.format(responseStatus.message(), params));
        this.code = responseStatus.code();
    }

    public static BusinessException responseStatus(ResponseStatus responseStatus) {
        return new BusinessException(responseStatus);
    }

    public static BusinessException responseStatus(ResponseStatus responseStatus, String... params) {
        return new BusinessException(responseStatus, params);
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNotEmpty(this.messageKey)) {
            return MessageUtils.getMessage(this.messageKey, this.messageArgs);
        } else if (StringUtils.isNotEmpty(message)) {
            return message;
        }
        return super.getMessage();
    }

}
