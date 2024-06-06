package com.example.sampleapp.demo.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class AccessLoggingFilter extends OncePerRequestFilter {
    private final String STATUS_CODE = "status_code";
    private final String REQUEST_URL = "request_url";
    private final String LATENCY = "latency";
    private final String UNIQUE_ID = "uuid";
    private final String METHOD = "method";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put(UNIQUE_ID, UUID.randomUUID().toString());
            MDC.put(REQUEST_URL, request.getServletPath());
            MDC.put(METHOD, request.getMethod());
            long start = System.currentTimeMillis();

            filterChain.doFilter(request, response);

            long end = System.currentTimeMillis();

            MDC.put(LATENCY, Long.toString(end - start) + " ms");
            MDC.put(STATUS_CODE, Integer.toString(response.getStatus()));

            log.info("");
        } finally {
            MDC.remove(UNIQUE_ID);
            MDC.remove(REQUEST_URL);
            MDC.remove(LATENCY);
            MDC.remove(STATUS_CODE);
            MDC.remove(METHOD);
        }

    }
}
