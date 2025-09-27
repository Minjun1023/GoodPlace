package com.example.GoodPlace.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("GoodPlace 회원가입 이메일 인증");

            // The verification URL now points to the frontend
            String verificationUrl = "http://localhost:3000/verify-email?token=" + token;
            String htmlContent = "<h1>GoodPlace 회원가입</h1>"
                    + "<p>회원가입을 완료하려면 아래 링크를 클릭하세요:</p>"
                    + "<a href=\"" + verificationUrl + "\">" + verificationUrl + "</a>";

            helper.setText(htmlContent, true); // true indicates the content is HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // In a real application, handle this exception properly
            throw new RuntimeException("이메일 발송에 실패했습니다.", e);
        }
    }
}
