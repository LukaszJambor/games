package com.example2.demo.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && !"anonymousUser".equals(authentication.getPrincipal())) {
            return ((User) authentication.getPrincipal()).getUsername();
        }
        return Strings.EMPTY;
    }
}