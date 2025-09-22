package com.example.GoodPlace.domain.user.dto;

import com.example.GoodPlace.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String name;    // 사용자 이름
    private String email;   // 사용자 이메일

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

}
