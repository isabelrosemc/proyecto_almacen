package com.almacen.ms_ventas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ventasOpenAPI() {

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8092");
        localServer.setDescription("Servidor Local - Microservicio de Ventas");

        Info info = new Info()
                .title("API de Microservicio de Ventas (ms-ventas)")
                .version("1.0.0")
                .description("Este microservicio se encarga de consolidar datos de diferentes módulos para generar las ventas dentro del sistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
