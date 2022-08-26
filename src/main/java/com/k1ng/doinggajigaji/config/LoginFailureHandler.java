package com.k1ng.doinggajigaji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("exception={}", exception.getMessage());
        log.info("exception={}", exception.getClass());

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        String errorMsg = "";

        if (exception instanceof BadCredentialsException) {
            errorMsg = "없는 이메일이거나 비밀번호가 일치하지 않습니다.";
            log.info("BadCredentialsException={}", errorMsg);

        } else if (exception instanceof DisabledException) {
            errorMsg = "이메일 인증을 완료해 주세요.";
            log.info("DisabledException={}", errorMsg);
        }

        request.setAttribute("errorMsg", errorMsg);
        log.info("errorMsg={}", errorMsg);

        request.getRequestDispatcher("/login/error").forward(request, response);
    }
}
