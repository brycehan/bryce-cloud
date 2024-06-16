package com.brycehan.cloud.email.service;

import com.brycehan.cloud.api.email.entity.ToMail;
import com.brycehan.cloud.common.core.enums.EmailType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 邮件服务
 *
 * @author Bryce Han
 * @since 2023/10/8
 */
public interface EmailService {

    /**
     * 发送简单邮件
     * @param toEmail 收邮件参数
     */
    void sendSimpleEmail(ToMail toEmail);

    /**
     * 发送html邮件
     * @param toEmail 收邮件参数
     * @param file 附件
     */
    void sendHtmlEmail(ToMail toEmail, MultipartFile file);

    /**
     * 发送邮件
     * @param toEmail 收邮件参数
     * @param emailType 邮件类型
     */
    void send(ToMail toEmail, EmailType emailType);

}
