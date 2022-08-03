package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.service.LikesService;
import com.k1ng.doinggajigaji.service.MemberService;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final PostService postService;
    private final MemberService memberService;
    private final LikesService likesService;

    @GetMapping("/")
    public String homepage(@ModelAttribute("post") PostFormDto postFormDto, Model model) {
        List<CardFormDto> allCard = postService.getAllCardForm();
        model.addAttribute("cards", allCard);
        return "index";
    }

}
