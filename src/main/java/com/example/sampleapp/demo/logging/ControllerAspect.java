package com.example.sampleapp.demo.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Around("execution(* com.example.sampleapp.demo.service.*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        try {
            String methodName = pjp.getSignature().getDeclaringType().getSimpleName() + "#" + pjp.getSignature().getName();
            log.info("Start: {}", methodName);
            Object obj = pjp.proceed();
            log.info("end: {}", methodName);
            return obj;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
