package com.example.GoodPlace.domain.user.dto;

import com.example.GoodPlace.domain.user.entity.Role;
import com.example.GoodPlace.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;
    private String providerId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String provider, String providerId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String id = attributes.get(userNameAttributeName).toString();
        if (email == null) {
            email = "kakao_" + id + "@kakao.com";
        }

        String name = (String) kakaoProfile.get("nickname");
        if (name == null) {
            name = "사용자";
        }

        OAuthAttributes result = OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("kakao")
                .providerId(id)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

        return result;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        String email = (String) attributes.get("email");
        String id = attributes.get(userNameAttributeName).toString();
        if (email == null) {
            email = "google_" + id + "@google.com";
        }

        String name = (String) attributes.get("name");
        if (name == null) {
            name = "사용자";
        }

        OAuthAttributes result = OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("google")
                .providerId(id)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

        return result;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String email = (String) response.get("email");
        String id = (String) response.get("id");
        if (email == null) {
            email = "naver_" + id + "@naver.com";
        }

        String name = (String) response.get("name");
        if (name == null) {
            name = (String) response.get("nickname");
        }
        if (name == null) {
            name = "사용자";
        }

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("naver")
                .providerId(id)
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(provider + "_" + providerId) // provider와 providerId를 조합하여 고유한 username 생성
                .nickname(name) // 소셜 로그인에서 받은 이름을 nickname으로 사용
                .email(email)
                .password("") // 소셜 로그인은 비밀번호가 없으므로 빈 값 저장
                .role(Role.USER)
                .enabled(true) // 소셜 로그인은 이메일 인증이 필요 없으므로 바로 활성화
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}