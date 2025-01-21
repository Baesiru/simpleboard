package com.mycompany.simpleboard.service;

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
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "[SimpleBoard] 비밀번호 변경을 위한 이메일 인증 메일입니다.";
        String body = "이메일 인증 번호 : " + code + "<br>해당 코드를 입력해주세요";
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
