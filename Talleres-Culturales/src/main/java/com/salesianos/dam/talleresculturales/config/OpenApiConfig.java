package com.salesianos.dam.talleresculturales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI talleresOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Talleres Culturales API")
                        .version("1.0.0")
                        .description("""
                                API REST para gestionar organizadores, talleres y participantes, \
                                incluyendo las inscripciones (relación N:M entre talleres y participantes).
                                
                                Los mensajes de error se devuelven como `ProblemDetail` (RFC 9457) \
                                y están internacionalizados (es / en) según la cabecera `Accept-Language`.""")
                        .contact(new Contact().name("Proyecto AD-PSP 2026-27")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local")));
    }

}
