package com.k1ng.doinggajigaji.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class Utility {
    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        log.info("siteURL={}", siteURL);
        // 컨텍스트 패스를 읽어온다.
        return siteURL.replace(request.getServletPath(), "");
    }
}
