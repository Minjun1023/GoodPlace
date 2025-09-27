package com.example.GoodPlace.service;

import com.example.GoodPlace.domain.user.entity.EmailVerification;
import com.example.GoodPlace.domain.user.repository.EmailVerificationRepository;
import com.example.GoodPlace.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    @Transactional
    public void requestVerification(String email) {
        // Check if user with this email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // Find existing verification request or create a new one
        EmailVerification verification = emailVerificationRepository.findByEmail(email)
                .orElse(new EmailVerification(email, UUID.randomUUID().toString()));

        // If it's a new one or expired, generate a new token and send email
        if (verification.getId() == null || verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            String token = UUID.randomUUID().toString();
            EmailVerification newVerification = new EmailVerification(email, token);
            emailVerificationRepository.save(newVerification);
            emailService.sendVerificationEmail(email, token);
        } else {
            // If a valid token was sent recently, throw an exception to prevent spamming
            throw new IllegalStateException("이미 인증 메일이 발송되었습니다. 5분 후에 다시 시도해주세요.");
        }
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerification verification = emailVerificationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증 토큰입니다."));

        if (verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            emailVerificationRepository.delete(verification);
            throw new IllegalArgumentException("인증 토큰이 만료되었습니다. 다시 요청해주세요.");
        }

        verification.setVerified();
        emailVerificationRepository.save(verification);
    }
}
