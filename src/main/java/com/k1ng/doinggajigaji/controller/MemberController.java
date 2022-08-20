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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
    public String memberForm(Model model) {
        model.addAttribute("member", new MemberFormDto());
        return "member/signup";
    }

    @PostMapping("/new")
    public String join(@Validated @ModelAttribute("member") MemberFormDto memberFormDto,
                       BindingResult bindingResult, HttpServletRequest request) {

        if (memberService.findDuplication(memberFormDto.getEmail())) {
            bindingResult.rejectValue("email", "duplicated.email");
            return "member/signup";
        }

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        Member member = Member.createMember(memberFormDto, passwordEncoder);

        try {
            memberService.join(member, getSiteURL(request));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "checkEmailVerification";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (memberService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    // 프로필 페이지
    @GetMapping("/profile")
    public String profileForm(Principal principal, Model model) {
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
            br.rejectValue("password", "passwordNotMatch", "계정 비밀번호가 올바르지 않습니다.");
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
            memberService.updatePassword(memberId, passwordChangeDto, passwordEncoder);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/profile";
        }
        return "redirect:/member/profile";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "member/deleteMemberForm";
    }

    @PostMapping("/delete")
    public String deleteMember(@RequestParam String rawPassword, Model model, Principal principal) {
        Member foundMember = memberService.findMemberByEmail(principal.getName());

        if (passwordEncoder.matches(rawPassword, foundMember.getPassword())) {
            memberService.deleteMember(foundMember);
        } else {
            model.addAttribute("errorMessage", "계정 비밀번호가 일치하지 않습니다.");
            return "member/deleteMemberForm";
        }
        return "redirect:/login";
    }

}
