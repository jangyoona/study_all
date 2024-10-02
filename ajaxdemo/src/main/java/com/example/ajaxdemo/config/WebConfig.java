package com.example.ajaxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 어디에 허용할건지 지정
                .allowedOriginPatterns("*") // 어떤 곳에서 오는걸 풀어줄거야
                .allowedMethods("GET", "POST") // 어떤 Method를
                .allowCredentials(true); // 쿠키
    }
}
