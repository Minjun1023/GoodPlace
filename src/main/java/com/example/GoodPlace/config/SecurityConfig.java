package com.example.GoodPlace.config;

import com.example.GoodPlace.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // 아래 URL들은 로그인 없이 누구나 접근 가능
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                        // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능
                        .requestMatchers("/api/v1/**").hasRole("USER")
                        // 나머지 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        // 로그아웃 성공 시 / 주소로 이동
                        .logoutSuccessUrl("/")
                )
                // OAuth2 로그인 기능에 대해 여러 설정의 진입점
                .oauth2Login(oauth2 -> oauth2
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint(userInfo -> userInfo
                                // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }
}
