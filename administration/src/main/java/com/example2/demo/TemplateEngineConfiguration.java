package com.example2.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class TemplateEngineConfiguration {

    @Bean
    public TemplateEngine templateEngine() {
        return new TemplateEngine();
    }
}
