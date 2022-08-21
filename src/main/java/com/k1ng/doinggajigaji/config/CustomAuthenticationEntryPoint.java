package com.k1ng.doinggajigaji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException authException) throws IOException, ServletException {

        log.info("authException = {}", authException.getMessage());
        StackTraceElement[] stackTrace = authException.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            log.info("stackTraceElement={}", stackTraceElement);

        }

        response.sendRedirect("/login");
    }
}
