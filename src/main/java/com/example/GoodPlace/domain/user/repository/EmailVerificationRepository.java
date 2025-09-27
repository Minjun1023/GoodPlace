package com.example.GoodPlace.domain.user.repository;

import com.example.GoodPlace.domain.user.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByToken(String token);

    Optional<EmailVerification> findByEmail(String email);
}
