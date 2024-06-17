package com.brycehan.cloud.api.email.fallback;

import com.brycehan.cloud.api.email.api.EmailApi;
import com.brycehan.cloud.api.email.entity.ToMailDto;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import com.brycehan.cloud.common.core.enums.EmailType;
import com.brycehan.cloud.common.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 邮件服务熔断降级处理
 *
 * @author Bryce Han
 * @since 2024/5/12
 */
@Slf4j
@Component
public class EmailApiFallbackImpl implements FallbackFactory<EmailApi> {

    @Override
    public EmailApi create(Throwable cause) {
        log.error("邮件服务调用失败，{}", cause.getMessage());
        return new EmailApi() {
            @Override
            public ResponseResult<Void> sendSimpleEmail(ToMailDto toMailDto) {
                return ResponseResult.fallback("邮件服务调用失败");
            }

            @Override
            public ResponseResult<Void> sendHtmlEmail(ToMailDto toMailDto, MultipartFile[] file) {
                return ResponseResult.fallback("邮件服务调用失败");
            }

            @Override
            public ResponseResult<Boolean> send(ToVerifyCodeEmailDto toVerifyCodeEmailDto, EmailType emailType) {
                return ResponseResult.fallback("邮件服务调用失败");
            }
        };
    }
}

