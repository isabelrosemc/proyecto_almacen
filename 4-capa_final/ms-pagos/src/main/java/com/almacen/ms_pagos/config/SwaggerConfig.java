package com.almacen.ms_pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pagosOpenAPI() {

        Server localServer = new Server();
        // Nota: Cambia el puerto si tu ms-pagos usa uno diferente en local
        localServer.setUrl("http://localhost:8091");
        localServer.setDescription("Servidor Local - Microservicio de Pagos");

        Info info = new Info()
                .title("API de Microservicio de Pagos (ms-pagos)")
                .version("1.0.0")
                .description("Este microservicio gestiona las transacciones financieras, registros de pagos, estados y pasarelas dentro del ecosistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}