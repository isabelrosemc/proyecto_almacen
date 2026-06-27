package com.almacen.ms_productos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI productosOpenAPI() {
        // Define el servidor local usando el puerto 8086 de tu configuración
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8086");
        localServer.setDescription("Servidor Local - Microservicio de Productos");

        // Información general de la API
        Info info = new Info()
                .title("API de Microservicio de Productos (ms-productos)")
                .version("1.0.0")
                .description("Este microservicio se encarga de la gestión del catálogo de productos, stock y operaciones relacionadas dentro del sistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}