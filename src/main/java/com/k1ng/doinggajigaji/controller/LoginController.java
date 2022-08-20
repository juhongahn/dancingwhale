package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.LoginFormDto;
import com.k1ng.doinggajigaji.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        model.addAttribute("login", new LoginFormDto());
        return "login";
    }

    @PostMapping("/login/error")
    public String loginError() {
        return "login";
    }

}
