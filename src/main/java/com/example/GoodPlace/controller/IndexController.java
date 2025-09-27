package com.example.GoodPlace.controller;

import com.example.GoodPlace.config.auth.LoginUser;
import com.example.GoodPlace.domain.store.dto.StoreListResponseDto;
import com.example.GoodPlace.domain.user.dto.SessionUser;
import com.example.GoodPlace.service.StoreService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final StoreService storeService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            List<StoreListResponseDto> stores = storeService.findAllByUser(user.getEmail());
            model.addAttribute("stores", stores);
        } else {
            model.addAttribute("stores", Collections.emptyList());
        }
        return "index";
    }
}
