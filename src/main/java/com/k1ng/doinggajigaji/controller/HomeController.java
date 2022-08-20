package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final PostService postService;

    @GetMapping(value = {"/"})
    public String homepage(@ModelAttribute("post") PostFormDto postFormDto,
                           Model model, Principal principal,
                           @PageableDefault(size = 5) Pageable pageable) {


        // 필요한것들 1. 작성자 2. 사진 3. 좋아요 갯수 4. 글
        // 작성자, 사진, 글은 post 엔티티에서 가져올 수 있다.
        Page<CardFormDto> allCardForm = postService.getAllCardForm(principal.getName(), pageable);
        String nlString = System.getProperty("line.separator");
        model.addAttribute("nlString", nlString);
        model.addAttribute("cards", allCardForm);
        return "index";
    }

}
