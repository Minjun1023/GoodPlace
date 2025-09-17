package com.example.GoodPlace.controller;

import com.example.GoodPlace.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        // CustomOAuth2Service 에서 섹션에 저장한 사용자 정보를 가져 온다
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            // 세션에 정보가 있을 경우 모델에 userName 으로 등록
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
}
