package com.udacity.vehicles.config;


import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Vehicles API")
                        .version("1.0")
                        .description("API documentation for the Vehicles API"));
    }
}
