package com.example.GoodPlace.controller;

import com.example.GoodPlace.config.auth.LoginUser;
import com.example.GoodPlace.domain.user.dto.SessionUser;
import com.example.GoodPlace.domain.user.dto.UserSignupRequestDto;
import com.example.GoodPlace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<SessionUser> getCurrentUser(@LoginUser SessionUser user) {
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }
}
