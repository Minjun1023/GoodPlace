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
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 아이디

    @Column(nullable = false, unique = true)
    private String nickname; // 닉네임

    @Column(nullable = false, unique = true)
    private String email;   // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // 권한 (GUEST, USER)

    @Column(nullable = false)
    private boolean enabled; // 계정 활성화 여부

    private String provider;    // 소셜 서비스 이름
    private String providerId;  // 소셜 서비스의 고유 ID

    @Builder
    public User(String username, String nickname, String email, String password, Role role, boolean enabled, String provider, String providerId) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.provider = provider;
        this.providerId = providerId;
    }

    // OAuth2 사용자 정보 업데이트용
    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }

    // 이메일 인증 후 계정 활성화
    public void enable() {
        this.enabled = true;
    }
}
