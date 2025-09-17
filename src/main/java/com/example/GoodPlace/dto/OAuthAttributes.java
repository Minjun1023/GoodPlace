package com.example.GoodPlace.dto;

import com.example.GoodPlace.entity.Role;
import com.example.GoodPlace.entity.User;
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
            return ofNaver("id", attributes);
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

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("kakao")
                .providerId(id)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
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

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("google")
                .providerId(id)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String email = (String) response.get("email");
        String id = (String) response.get(userNameAttributeName);
        if (email == null) {
            email = "naver_" + id + "@naver.com";
        }

        String name = (String) response.get("name");
        if (name == null) {
            name = "사용자";
        }

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .provider("naver")
                .providerId(id)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}