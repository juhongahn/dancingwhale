package com.k1ng.doinggajigaji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException authException) throws IOException, ServletException {


        log.info("authException = {}", authException.getMessage());
        log.info("authException = {}", authException.getClass());

        response.sendRedirect("/login");
    }
}
