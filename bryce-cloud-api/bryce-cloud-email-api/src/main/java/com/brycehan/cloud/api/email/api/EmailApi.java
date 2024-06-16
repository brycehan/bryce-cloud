package com.brycehan.cloud.api.email.api;

import com.brycehan.cloud.api.email.entity.ToMail;
import com.brycehan.cloud.api.email.fallback.EmailApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.EmailType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 邮件Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@Tag(name = "邮件Api")
@FeignClient(name = ServerNames.BRYCE_CLOUD_EMAIL, path = EmailApi.PATH, contextId = "email", fallbackFactory = EmailApiFallbackImpl.class)
public interface EmailApi {

    String PATH = "/api/email";

    /**
     * 发送简单邮件
     *
     * @param toEmail 收邮件参数
     * @return 响应结果
     */
    @PostMapping(path = "/sendSimpleEmail")
    ResponseResult<Void> sendSimpleEmail(@Validated @RequestBody ToMail toEmail);

    /**
     * 发送附件邮件
     *
     * @param toEmail 收邮件参数
     * @param file 附件
     * @return 响应结果
     */
    @PostMapping(path = "/sendHtmlEmail")
    ResponseResult<Void> sendHtmlEmail(@Validated @RequestBody ToMail toEmail, MultipartFile file);

    /**
     * 发送验证码邮件
     *
     * @param toEmail 收邮件参数
     * @param emailType 邮件类型
     * @return 是否发送成功
     */
    @Operation(summary = "发送验证码邮件，emailType有login、register")
    @PostMapping(path = "/sendValidateCode/{emailType}")
    ResponseResult<Boolean> send(@Validated @RequestBody ToMail toEmail, @PathVariable @RequestParam EmailType emailType);

}
