package com.brycehan.cloud.email.api;

import com.brycehan.cloud.api.email.api.EmailApi;
import com.brycehan.cloud.api.email.entity.ToMailDto;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import com.brycehan.cloud.common.core.enums.EmailType;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 邮件Api
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "邮件")
@RequestMapping(path = EmailApi.PATH)
@RestController
@RequiredArgsConstructor
public class EmailApiController implements EmailApi {

    private final EmailService emailService;

    /**
     * 发送简单邮件
     *
     * @param toMailDto 邮件DTO
     * @return 响应结果
     */
    @Override
    @Operation(summary = "发送简单邮件")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Void> sendSimpleEmail(ToMailDto toMailDto) {
        this.emailService.sendSimpleEmail(toMailDto);
        return ResponseResult.ok();
    }

    /**
     * 发送附件邮件
     *
     * @param toMailDto 邮件DTO
     * @param file      附件
     * @return 响应结果
     */
    @Override
    @Operation(summary = "发送附件邮件")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Void> sendHtmlEmail(ToMailDto toMailDto, MultipartFile[] file) {
        this.emailService.sendHtmlEmail(toMailDto, file);
        return ResponseResult.ok();
    }

    /**
     * 发送验证码邮件
     *
     * @param toVerifyCodeEmailDto 邮件DTO
     * @param emailType            邮件类型
     * @return 响应结果
     */
    @Override
    @Operation(summary = "发送验证码邮件")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<Boolean> send(ToVerifyCodeEmailDto toVerifyCodeEmailDto, EmailType emailType) {
        this.emailService.send(toVerifyCodeEmailDto, emailType);
        return ResponseResult.ok(Boolean.TRUE);
    }

}
