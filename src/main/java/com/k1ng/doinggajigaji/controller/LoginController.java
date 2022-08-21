package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.LoginFormDto;
import com.k1ng.doinggajigaji.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class LoginController {

    // 로그인
    @GetMapping("/login")
    public String loginForm(Model model) {

        // 로그인한 유저가 로그인 페이지 다시 요청하는 것을 방지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        model.addAttribute("login", new LoginFormDto());
        return "redirect:/";
    }

    @PostMapping("/login/error")
    public String loginError() {
        return "login";
    }

}
