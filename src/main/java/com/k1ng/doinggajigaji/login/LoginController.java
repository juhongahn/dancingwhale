package com.k1ng.doinggajigaji.login;

import com.k1ng.doinggajigaji.constance.SessionConst;
import com.k1ng.doinggajigaji.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class LoginController {

    private final LoginService loginService;

    // 로그인
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("login", new LoginFormDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginFormDto loginFormDto, BindingResult br,
                         HttpServletRequest request, Model model){
        if (br.hasErrors()) {
            log.info(br.getAllErrors().toString());
            return "login";
        }
        Member loginMember = loginService.login(loginFormDto);
        if (loginMember == null) {
            br.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        // 로그인 성공 로직
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getEmail());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
