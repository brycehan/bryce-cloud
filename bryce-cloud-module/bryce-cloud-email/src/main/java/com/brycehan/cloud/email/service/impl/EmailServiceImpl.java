package com.brycehan.cloud.email.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.brycehan.cloud.api.email.entity.ToMailDto;
import com.brycehan.cloud.api.email.entity.ToVerifyCodeEmailDto;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.enums.EmailType;
import com.brycehan.cloud.common.core.util.FileUploadUtils;
import com.brycehan.cloud.common.core.util.MimeTypeUtils;
import com.brycehan.cloud.email.service.EmailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Objects;

/**
 * 邮件服务实现
 *
 * @author Bryce Han
 * @since 2023/10/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Resource
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value(DataConstants.COMPANY_NAME + "<${spring.mail.username}>")
    private String from;

    @Override
    public void sendSimpleEmail(ToMailDto toMailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(from);
        // 接收者
        message.setTo(toMailDto.getTos());
        // 抄送
        message.setCc(toMailDto.getCc());
        // 密送
        message.setBcc(toMailDto.getBcc());
        // 邮件主题
        message.setSubject(toMailDto.getSubject());
        // 邮件内容
        message.setText(toMailDto.getContent());
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("普通邮件发送失败");
        }
    }

    @Override
    public void sendHtmlEmail(ToMailDto toMailDto, List<MultipartFile> file) {
        try {
            // 创建一个MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = getMimeMessageHelper(toMailDto, message);

            // 添加附件
            if (CollUtil.isNotEmpty(file)) {
                file.stream().filter(Objects::nonNull).filter(f -> !f.isEmpty()).forEach(f -> {
                    FileUploadUtils.assertAllowed(f, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
                    try {
                        messageHelper.addAttachment(Objects.requireNonNull(f.getOriginalFilename()), f);
                    } catch (Exception e) {
                        throw new RuntimeException("邮件附件参数错误");
                    }
                });
            }

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败");
        }
    }

    @Override
    public void send(ToVerifyCodeEmailDto toVerifyCodeEmailDto, EmailType emailType) {
        ToMailDto toEmailDto = new ToMailDto();
        toEmailDto.setTos(new String[]{toVerifyCodeEmailDto.getTo()});

        // 参数封装
        Context context = new Context();
        context.setVariable("emailType", emailType.getDesc());
        context.setVariable("verifyCode", toVerifyCodeEmailDto.getVerifyCode());
        context.setVariable("companyName", DataConstants.COMPANY_NAME);

        toEmailDto.setSubject(DataConstants.COMPANY_EMAIL_VERIFY_CODE_SUBJECT + toVerifyCodeEmailDto.getVerifyCode());
        toEmailDto.setContent(templateEngine.process(TemplateIds.VERIFY_CODE, context));

        this.sendHtmlEmail(toEmailDto, null);
    }

    /**
     * 获取MimeMessageHelper
     *
     * @param toMailDto ToMailDto
     * @param message MimeMessage
     * @return MimeMessageHelper
     * @throws MessagingException MessagingException
     */
    private MimeMessageHelper getMimeMessageHelper(ToMailDto toMailDto, MimeMessage message) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        // 发送者
        messageHelper.setFrom(from);
        // 接收者
        messageHelper.setTo(toMailDto.getTos());

        // 抄送
        if (toMailDto.getCc() != null) {
            messageHelper.setCc(toMailDto.getCc());
        }

        // 密送
        if (toMailDto.getBcc() != null) {
            messageHelper.setBcc(toMailDto.getBcc());
        }

        // 邮件优先级（1:紧急 3:普通 5:低）
        messageHelper.setPriority(3);

        // 邮件主题
        messageHelper.setSubject(toMailDto.getSubject());

        // 邮件内容  true表示带有附件或html
        messageHelper.setText(toMailDto.getContent(), true);
        return messageHelper;
    }

    /**
     * 模板ID
     */
    static class TemplateIds {
        /**
         * 验证码
         */
        public static final String VERIFY_CODE = "verify-code";
    }

}
