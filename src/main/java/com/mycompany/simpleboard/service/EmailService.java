package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.user.MailErrorException;
import com.mycompany.simpleboard.config.exception.user.UserErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String configEmail;

    @Async
    public void sendMail(String to, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(configEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("[SimpleBoard] 비밀번호 변경을 위한 이메일 인증 메일입니다.");
            String body = "";
            body += "<h3>" + "SimpleBoard 비밀번호 변경을 위한 인증 번호입니다." + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "해당 코드를 입력해주세요." + "</h3>";
            message.setText(body,"UTF-8", "html");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailErrorException(UserErrorCode.MAIL_SEND_ERROR);
        }
    }
}
