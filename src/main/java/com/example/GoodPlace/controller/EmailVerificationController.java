package com.example.GoodPlace.controller;

import com.example.GoodPlace.domain.user.dto.EmailRequestDto;
import com.example.GoodPlace.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/email-verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/request")
    public ResponseEntity<String> requestVerificationEmail(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        try {
            emailVerificationService.requestVerification(emailRequestDto.getEmail());
            return ResponseEntity.ok("인증 메일이 성공적으로 발송되었습니다. 이메일을 확인해주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        try {
            emailVerificationService.verifyEmail(token);
            return ResponseEntity.ok("이메일 인증이 성공적으로 완료되었습니다. 회원가입을 계속 진행해주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
