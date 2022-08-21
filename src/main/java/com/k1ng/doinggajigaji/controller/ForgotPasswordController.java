package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.service.MemberService;
import com.k1ng.doinggajigaji.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {

    private final JavaMailSender mailSender;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPasswordForm";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");

        if (email.equals("")) {
            model.addAttribute("error", "이메일을 입력해 주세요.");
            return "forgotPasswordForm";
        }
        String token = RandomString.make(30);

        try {
            memberService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
            // 이메일에서 링크를 클릭하면 비밀번호 리셋 페이지로 이동한다.
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "비밀번호 초기화 링크를 회원님의 이메일로 전송했습니다.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "이메일을 전송하던 중 문제가 발생했습니다.");
        }

        return "forgotPasswordForm";
    }

    public void sendEmail(String memberEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String senderName = "춤추는 고래";
        helper.setFrom("dancingWhale@gmail.com", senderName);
        helper.setTo(memberEmail);

        String subject = "비밀번호 초기화 링크입니다.";
        String content = "<p>안녕하세요!</p>"
                + "<p>아래 링크를 클릭해 비밀번호를 초기화 하세요.</p>"
                + "<p><a href=\"" + link + "\">비밀번호 변경</a></p>"
                + "<br>"
                + "<p>비밀번호가 기억나셨다면 이 이메일을 무시하세요. </p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {

        Member member = memberService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (member == null) {
            model.addAttribute("message", "유효하지 않은 토큰입니다.");
            return "message";
        }
        return "resetPasswordForm";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Member member = memberService.getByResetPasswordToken(token);

        model.addAttribute("title", "비밀번호를 초기화 하세요.");

        if (member == null) {
            model.addAttribute("message", "유효하지 않은 토큰입니다.");
            return "message";
        } else {
            memberService.updatePassword(member, password, passwordEncoder);

            model.addAttribute("message", "성공적으로 비밀번호가 초기화 됐습니다.");
        }
        return "message";
    }
}
