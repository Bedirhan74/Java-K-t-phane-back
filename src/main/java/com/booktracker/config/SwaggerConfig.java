package com.booktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kitap Takip Uygulamasu0131 API")
                        .description("Spring Boot, MongoDB ve Thymeleaf ile geliu015ftirilmiu015f kitap takip uygulamasu0131 API'si")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kitap Takip Uygulamasu0131")
                                .email("info@booktracker.com")
                                .url("https://booktracker.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
