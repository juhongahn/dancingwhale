package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import com.k1ng.doinggajigaji.dto.MemberFormDto;
import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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
                       BindingResult bindingResult, Model model) {
        /**
         * TODO 한 가지만 검증오류 출력하기.
          */

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        try {
            Member member = Member.formToMember(memberFormDto);
            memberService.join(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    // 프로필 페이지
    @GetMapping("/profile")
    public String profileForm(@Login String email, Model model) {
        Member member = memberService.findMemberByEmail(email);
        model.addAttribute("profile", ProfileDto.of(member));
        return "member/profile";
    }

    // 회원 수정
    @GetMapping("/{memberId}/edit")
    public String editForm(@PathVariable Long memberId, Model model) {

        try {
            Member member = memberService.findMemberById(memberId);
            model.addAttribute("profile", ProfileEditDto.of(member));

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMsg", "없는 유저입니다.");
            return "/login";
        }
        return "member/editForm";
    }

    @PostMapping("/{memberId}/edit")
    public String editProfile(@Validated @ModelAttribute("profile") ProfileEditDto profileEditDto,
                       BindingResult br, Model model, @PathVariable Long memberId) {
        log.info("ProfileEditDto {}", profileEditDto);

        if (br.hasErrors()) {
            return "member/editForm";
        }

        if (!memberService.checkPassword(memberId, profileEditDto.getPassword())) {
            br.reject("passwordNotMatch", "계정 비밀번호가 올바르지 않습니다.");
            return "member/editForm";
        }
        try {
            memberService.updateProfile(memberId, profileEditDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/profile";
        }
        return "redirect:/member/profile";
    }
    
    // 비밀번호 변경
    @GetMapping("/{memberId}/password/edit")
    public String passwordEditForm(@PathVariable Long memberId, Model model) {

        model.addAttribute("passwordEditDto", new PasswordChangeDto());
        return "member/passwordEditForm";
    }

    @PostMapping("/{memberId}/password/edit")
    public String editProfile(@Validated @ModelAttribute("profile") PasswordChangeDto passwordChangeDto,
                              BindingResult br, Model model, @PathVariable Long memberId) {

        log.info("passwordChangeDto = {}", passwordChangeDto);

        if (br.hasErrors()) {
            return "member/passwordEditForm";
        }

        if (!passwordChangeDto.getInputPassword().equals(passwordChangeDto.getConfirmPassword())) {
            br.reject("inputPasswordNotMatch", "입력하신 두 비밀번호가 일치하지 않습니다.");
            return "member/passwordEditForm";
        }


        if (!memberService.checkPassword(memberId, passwordChangeDto.getCurrentPassword())) {
            br.reject("passwordNotMatch", "계정 비밀번호가 올바르지 않습니다.");
            return "member/passwordEditForm";
        }
        try {
            memberService.updatePassword(memberId, passwordChangeDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/profile";
        }
        return "redirect:/member/profile";
    }

}
