package com.example.GoodPlace.domain.user.dto;

import com.example.GoodPlace.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String nickname;    // 사용자 닉네임
    private String email;       // 사용자 이메일

    public SessionUser(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

}