package com.k1ng.doinggajigaji.post;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public String posting(@Validated @ModelAttribute("post") PostFormDto postFormDto, BindingResult br,
                          @Login String email) {

        log.info(postFormDto.toString());
        if (br.hasErrors()) {
            return "/";
        }
        postService.post(postFormDto, email);
        return "redirect:/";
    }



}
