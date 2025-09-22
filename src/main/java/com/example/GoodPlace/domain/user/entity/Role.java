package com.example.GoodPlace.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),  // 게스트 권한
    USER("ROLE_USER", "일반 사용자");    // 일반 사용자 권한

    private final String key;   // Spring Security에서 사용하는 권한 키
    private final String title; // 사용자에게 보여줄 권한 이름
}
