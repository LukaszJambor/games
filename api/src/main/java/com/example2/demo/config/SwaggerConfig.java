package com.example2.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Games shop")
                        .description("Spring games shop application")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Project documentation will be under this link in future")
                        .url("https://github.com/LukaszJambor/games"));
    }
}
