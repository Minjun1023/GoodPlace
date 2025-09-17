package com.example.GoodPlace.service;

import com.example.GoodPlace.Repository.UserRepository;
import com.example.GoodPlace.dto.OAuthAttributes;
import com.example.GoodPlace.dto.SessionUser;
import com.example.GoodPlace.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드 (구글, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드값. Primary Key와 같은 의미.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    @Transactional
    public User saveOrUpdate(OAuthAttributes attributes) {
        // 이메일로 사용자를 찾는다
        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail());

        User user;
        if (userOptional.isPresent()) {
            // 이미 가입 사용자일 경우, 이름 정보만 업데이트
            user = userOptional.get();
            user.update(attributes.getName());
        } else {
            // 처음 가입하는 사용자일 경우, User 엔티티 생성
            user = attributes.toEntity();
        }

        // DB에 저장, 이미 있는 사용자는 업데이트된 정보가, 없는 사용자는 새로 INSERT
        return userRepository.save(user);
    }
}

