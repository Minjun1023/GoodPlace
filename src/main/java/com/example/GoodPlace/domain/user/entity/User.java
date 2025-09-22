package com.example.GoodPlace.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 사용자 고유 ID

    @Column(nullable = false)
    private String name;    // 사용자 이름

    @Column(nullable = false, unique = true)    // 이메일 중복 제거
    private String email;   // 사용자 이메일 (로그인 시 사용되는 주 식별자)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // 사용자의 권한 (GUEST, USER)

    private String provider;    // 로그인을 진행한 소셜 서비스 이름 (Kakao, Google, Naver)
    private String providerId;  // 해당 소셜 서비스에서 사용자를 식별하는 고유 ID

    @Builder
    public User(String name, String email, Role role, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }
}
