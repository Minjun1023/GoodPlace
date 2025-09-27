package com.example.GoodPlace.domain.user.dto;

import com.example.GoodPlace.domain.user.entity.Role;
import com.example.GoodPlace.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 영문 소문자, 숫자만 사용 가능합니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[! @#$%^&*]).{8,}$",
             message = "비밀번호는 8자 이상이며, 대문자, 소문자, 숫자, 특수문자(! @#$%^&*)를 각각 하나 이상 포함해야 합니다.")
    private String password;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .enabled(true) // 이메일 사전 인증 완료
                .build();
    }
}