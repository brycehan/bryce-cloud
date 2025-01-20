package com.brycehan.cloud.api.email.api;

import com.brycehan.cloud.api.email.entity.ToMailDto;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.EmailType;
import feign.Request;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 邮件Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface EmailApi {

    String PATH = "/api/email";

    /**
     * 发送简单邮件
     *
     * @param toMailDto 收邮件参数
     * @return 响应结果
     */
    @PostMapping(path = "/sendSimpleEmail")
    ResponseResult<Void> sendSimpleEmail(@Validated @RequestBody ToMailDto toMailDto);

    /**
     * 发送附件邮件
     *
     * @param toMailDto 收邮件参数
     * @param file 附件
     * @return 响应结果
     */
    @PostMapping(path = "/sendHtmlEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult<Void> sendHtmlEmail(@Validated @RequestPart ToMailDto toMailDto, @RequestPart List<MultipartFile> file, Request.Options options);

    /**
     * 发送验证码邮件
     *
     * @param toVerifyCodeEmailDto 收邮件参数
     * @param emailType 邮件类型
     * @return 是否发送成功
     */
    @PostMapping(path = "/sendValidateCodeEmail/{emailType}")
    ResponseResult<Boolean> send(@Validated @RequestBody ToVerifyCodeEmailDto toVerifyCodeEmailDto, @PathVariable EmailType emailType);

}
