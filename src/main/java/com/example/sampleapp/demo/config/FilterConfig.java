package com.example.sampleapp.demo.config;

import com.example.sampleapp.demo.logging.AccessLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AccessLoggingFilter> loggingFilter() {
        FilterRegistrationBean<AccessLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AccessLoggingFilter());
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }
}
