package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.constance.SessionConst;
import com.k1ng.doinggajigaji.member.service.MemberService;
import com.k1ng.doinggajigaji.post.Post;
import com.k1ng.doinggajigaji.post.PostFormDto;
import com.k1ng.doinggajigaji.post.PostService;
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

        List<Post> allPost = postService.findAllPost();
        if (!allPost.isEmpty())
            log.info(allPost.get(0).toString());

        model.addAttribute("posts", allPost);
        return "index";
    }

}
