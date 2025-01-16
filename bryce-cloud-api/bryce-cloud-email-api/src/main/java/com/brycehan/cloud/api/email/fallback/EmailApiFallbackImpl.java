package com.brycehan.cloud.api.email.fallback;

import com.brycehan.cloud.api.email.client.EmailClient;
import com.brycehan.cloud.api.email.entity.ToMailDto;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 邮件服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class EmailApiFallbackImpl implements FallbackFactory<EmailClient> {

    @Override
    public EmailClient create(Throwable cause) {
        log.error("邮件服务调用失败", cause);

        return new EmailClient() {
            @Override
            public ResponseResult<Void> sendSimpleEmail(ToMailDto toMailDto) {
                return ResponseResult.fallback("邮件服务调用失败");
            }

            @Override
            public ResponseResult<Void> sendHtmlEmail(ToMailDto toMailDto, List<MultipartFile> file) {
                return ResponseResult.fallback("邮件服务调用失败");
            }

            @Override
            public ResponseResult<Boolean> send(ToVerifyCodeEmailDto toVerifyCodeEmailDto, EmailType emailType) {
                return ResponseResult.fallback("邮件服务调用失败");
            }
        };
    }
}

