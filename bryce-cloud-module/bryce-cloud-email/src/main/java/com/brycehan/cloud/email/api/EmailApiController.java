package com.brycehan.cloud.email.api;

import com.brycehan.cloud.api.email.api.EmailApi;
import com.brycehan.cloud.api.email.entity.ToMail;
import com.brycehan.cloud.common.core.response.HttpResponseStatus;
import com.brycehan.cloud.common.core.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.EmailType;
import com.brycehan.cloud.email.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "邮件Api")
@RequestMapping(path = EmailApi.PATH)
@RestController
@RequiredArgsConstructor
public class EmailApiController implements EmailApi {

    private final EmailService emailService;

    @Override
    public ResponseResult<Void> sendSimpleEmail(@Validated @RequestBody ToMail toEmail) {
        this.emailService.sendSimpleEmail(toEmail);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult<Void> sendHtmlEmail(@Validated @RequestBody ToMail toEmail, MultipartFile file) {
        this.emailService.sendHtmlEmail(toEmail, file);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult<Boolean> send(ToMail toEmail, String emailType) {
        EmailType type = EmailType.getByValue(emailType);
        if (type == null) {
            return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST);
        }

        this.emailService.send(toEmail, type);
        return ResponseResult.ok(Boolean.TRUE);
    }

}
