package com.k1ng.doinggajigaji.member;

import com.k1ng.doinggajigaji.member.dto.MemberFormDto;
import com.k1ng.doinggajigaji.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("member", new MemberFormDto());
        return "member/signup";
    }


    @PostMapping("/new")
    public String join(@Validated @ModelAttribute("member") MemberFormDto memberFormDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (memberService.findDuplication(memberFormDto.getEmail())) {
            bindingResult.rejectValue("email", "duplicated");
        }

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        memberService.join(memberFormDto);
        redirectAttributes.addAttribute("signup", true);
        return "redirect:/";
    }



    // 회원 수정

    // 회원 탈퇴


}
