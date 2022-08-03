package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.constant.SessionConst;
import com.k1ng.doinggajigaji.service.LikesService;
import com.k1ng.doinggajigaji.service.MemberService;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final PostService postService;
    private final MemberService memberService;
    private final LikesService likesService;

    @GetMapping("/")
    public String homepage(@ModelAttribute("post") PostFormDto postFormDto, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        String email = (String)session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info(email);
        //세션에 회원 데이터가 없으면 home
        if (!memberService.findDuplication(email)) {
            return "redirect:/login";
        }
        List<Post> allPost = postService.findAllByOrderByCreatedAtDesc();
        model.addAttribute("posts", allPost);
        return "index";
    }

}
