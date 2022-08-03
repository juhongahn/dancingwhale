package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @GetMapping("/new")
    public String postForm(Model model) {
        PostFormDto postFormDto = new PostFormDto();
        model.addAttribute("post", postFormDto);
        return null;
    }

    @PostMapping("/new")
    public String posting(@Validated @ModelAttribute("post") PostFormDto postFormDto, BindingResult br,
                        @Login String email, Model model) {

        if (br.hasErrors()) {
            return "index";
        }
        try {
            postService.savePost(postFormDto, email);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "index";
        }
        return "redirect:/";
    }

    // 게시물 수정

    @GetMapping("/{postId}/edit")
    public String postUpdateForm(@PathVariable Long postId, Model model) {

        // 없으면 EntityNotFoundException를 던진는데, ExceptionHandler로 잡자.
        PostFormDto post = postService.findPostById(postId);

        model.addAttribute("post", post);
        return null;
    }

    @PostMapping("/{postId}/edit")
    public String postUpdate(@Validated PostFormDto postFormDto, BindingResult br,
                             @PathVariable Long postId, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("error", "수정에 실패했습니다.");
            return "index";
        }

        try {
            postService.updatePost(postFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "index";
        }

        return "redirect:/";
    }





}
