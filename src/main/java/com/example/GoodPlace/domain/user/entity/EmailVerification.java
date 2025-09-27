package com.example.GoodPlace.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean verified;

    public EmailVerification(String email, String token) {
        this.email = email;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusMinutes(10); // 10분 후 만료
        this.verified = false;
    }

    public void setVerified() {
        this.verified = true;
    }
}
