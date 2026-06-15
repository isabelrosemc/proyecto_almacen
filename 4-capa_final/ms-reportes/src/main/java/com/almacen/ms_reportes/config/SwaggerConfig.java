package com.almacen.ms_reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reportesOpenAPI() {

        Server localServer = new Server();
        // Nota: Cambia el puerto si tu ms-reportes usa uno diferente en local (ej. 8086)
        localServer.setUrl("http://localhost:8093");
        localServer.setDescription("Servidor Local - Microservicio de Reportes");

        Info info = new Info()
                .title("API de Microservicio de Reportes (ms-reportes)")
                .version("1.0.0")
                .description("Este microservicio se encarga de consolidar datos de diferentes módulos para generar análisis, reportes estadísticos y auditorías dentro del sistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}