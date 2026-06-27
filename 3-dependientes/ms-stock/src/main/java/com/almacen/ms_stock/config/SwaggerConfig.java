package com.almacen.ms_stock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI stockOpenAPI() {

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8087");
        localServer.setDescription("Servidor Local - Microservicio de Stock");

        Info info = new Info()
                .title("API de Microservicio de Stock (ms-stock)")
                .version("1.0.0")
                .description("Este microservicio se encarga de la gestión de inventario y stock de productos dentro del sistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}