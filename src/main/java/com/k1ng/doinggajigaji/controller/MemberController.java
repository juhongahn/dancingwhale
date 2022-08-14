package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.MemberFormDto;
import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("member", new MemberFormDto());
        return "member/signup";
    }
    
    @PostMapping("/new")
    public String join(@Validated @ModelAttribute("member") MemberFormDto memberFormDto,
                       BindingResult bindingResult, Model model) {
        if (memberService.findDuplication(memberFormDto.getEmail())){
            bindingResult.rejectValue("email", "duplicated.email");
            return "member/signup";
        }

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.join(member);
        return "redirect:/";
    }

    // 프로필 페이지
    @GetMapping("/profile")
    public String profileForm(Principal principal, Model model) {
        log.info("email={}", principal.getName());
        Member member = memberService.findMemberByEmail(principal.getName());
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

        String rawPassword = profileEditDto.getPassword();
        String encodedPassword = memberService.findMemberById(memberId).getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            br.rejectValue("password","passwordNotMatch", "계정 비밀번호가 올바르지 않습니다.");
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

        model.addAttribute("passwordChangeDto", new PasswordChangeDto());

        return "member/passwordEditForm";
    }

    @PostMapping("/{memberId}/password/edit")
    public String editProfile(@Validated @ModelAttribute PasswordChangeDto passwordChangeDto,
                              BindingResult br, Model model, @PathVariable Long memberId) {

        log.info("passwordChangeDto = {}", passwordChangeDto);

        if (br.hasErrors()) {
            return "member/passwordEditForm";
        }

        if (!passwordChangeDto.getInputPassword().equals(passwordChangeDto.getConfirmPassword())) {
            br.rejectValue("confirmPassword", "inputPasswordNotMatch", "입력하신 두 비밀번호가 일치하지 않습니다.");
            return "member/passwordEditForm";
        }

        String rawPassword = passwordChangeDto.getCurrentPassword();
        String encodedPassword = memberService.findMemberById(memberId).getPassword();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            br.rejectValue("currentPassword", "passwordNotMatch", "계정 비밀번호가 올바르지 않습니다.");
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

    @GetMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);

        return "login";
    }

}
