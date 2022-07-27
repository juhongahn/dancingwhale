package com.k1ng.doinggajigaji.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("member", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String join(@ModelAttribute MemberFormDto memberFormDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Member member = new Member();
        member.setName(memberFormDto.getName());
        memberService.join(member);
        return "/";
    }


}
