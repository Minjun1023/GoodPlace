package com.example.GoodPlace.service;

import com.example.GoodPlace.domain.user.dto.UserSignupRequestDto;
import com.example.GoodPlace.domain.user.entity.EmailVerification;
import com.example.GoodPlace.domain.user.entity.User;
import com.example.GoodPlace.domain.user.repository.EmailVerificationRepository;
import com.example.GoodPlace.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(UserSignupRequestDto requestDto) {
        // 1. Check for uniqueness of username, email, nickname
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 2. Check if the email has been verified
        EmailVerification verification = emailVerificationRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증이 필요합니다."));

        if (!verification.isVerified()) {
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        // 3. Create and save the user (as enabled)
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity(encodedPassword);
        User savedUser = userRepository.save(user);

        // 4. Clean up the verification record
        emailVerificationRepository.delete(verification);

        return savedUser.getId();
    }
}