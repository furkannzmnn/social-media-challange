package com.example.socialmediachallange.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SOCIAL MEDIA API")
                        .version("1.0")
                        .description("CASE STUDY")
                        .license(new License().name("SOCIAL MEDIA API LICENCE")));
    }
}
