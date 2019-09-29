package com.example2.demo.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CheckUserAspect {

    @Around("@annotation(CheckUser)")
    public Object checkUser(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomUser user = (CustomUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CodeSignature codeSignature = ((CodeSignature) (joinPoint.getSignature()));
        String[] parameterNames = codeSignature.getParameterNames();
        int userIdIndex = Arrays.asList(parameterNames).indexOf("userId");
        if (!user.getUserId().equals(joinPoint.getArgs()[userIdIndex])) {
            throw new AccessDeniedException("no access");
        }
        return joinPoint.proceed();
    }
}
