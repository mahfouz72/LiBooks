package org.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your API Title")
                        .version("1.0")
                        .description("Your API Description")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com"))
                        .termsOfService("http://swagger.io/terms/"));
    }
}