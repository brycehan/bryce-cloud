package com.brycehan.cloud.framework.common;

import com.brycehan.cloud.common.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.base.http.ResponseResult;
import com.brycehan.cloud.common.base.http.UploadResponseStatus;
import com.brycehan.cloud.common.base.http.UserResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务器异常处理
 *
 * @since 2022/5/6
 * @author Bryce Han
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {

    @Value("spring.servlet.multipart.max-request-size")
    private String maxRequestSize;

    /**
     * 数据绑定异常处理
     *
     * @param e 数据绑定异常
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> handleException(BindException e) {
        String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), message);
    }

    /**
     * 访问不允许处理
     *
     * @param e 访问禁止异常
     * @return 响应结果
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<Void> handleException(AccessDeniedException e) {
        log.info(" 访问不允许异常，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatus.HTTP_FORBIDDEN);
    }

    /**
     * 密码错误处理
     *
     * @param e 密码错误异常
     * @return 响应结果
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult<Void> handleException(BadCredentialsException e) {
        log.info(" 密码错误异常，{}", e.getMessage());
        return ResponseResult.error(UserResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 上传文件异常
     * @param e 上传文件异常
     * @return 响应结果
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseResult<Void> handleException(MultipartException e) {
        log.info(" 上传异常，{}", e.getMessage());

        return ResponseResult.error(UploadResponseStatus.UPLOAD_EXCEED_MAX_SIZE, maxRequestSize);
    }

    /**
     * 请求方法不支持异常处理
     *
     * @param e 请求方法不支持异常
     * @return 响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Void> handleException(HttpRequestMethodNotSupportedException e) {
        return ResponseResult.error(HttpResponseStatus.HTTP_METHOD_NOT_ALLOWED, e.getMethod());
    }

    /**
     * 实体字段校验不通过异常处理
     *
     * @param e 实体字段校验不通过异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handleException(MethodArgumentNotValidException e) {
        List<String> errors = e.getFieldErrors().stream()
                .map(item ->  "字段" + item.getField() + item.getDefaultMessage())
                .collect(Collectors.toList());
        log.info("实体字段校验不通过异常", e);
        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), String.join(", ", errors));
    }

    /**
     * 内部认证服务异常处理
     *
     * @param e 内部认证服务异常
     * @return 响应结果
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult<Void> handleException(InternalAuthenticationServiceException e) {
        log.info("业务异常", e);
        return ResponseResult.error(UserResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR.code(), e.getMessage());
    }

    /**
     * 约束违反异常处理
     *
     * @param e 约束违反异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        log.error("数据校验异常", e);
        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), message);
    }



    /**
     * 用户账户没有启用异常处理
     *
     * @param e 用户账户没有启用异常
     * @return 响应结果
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseResult<Void> handleException(DisabledException e) {
        log.info("用户账户没有启用异常，{}", e.getLocalizedMessage());
        return ResponseResult.error(UserResponseStatus.USER_ACCOUNT_DISABLED);
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Void> handleException(BusinessException e) {
        log.info("业务异常", e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 通用异常处理
     *
     * @param e 一般异常
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception e) {
        log.info("系统内部异常", e);
        return ResponseResult.error(HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), e.getMessage());
    }
}
