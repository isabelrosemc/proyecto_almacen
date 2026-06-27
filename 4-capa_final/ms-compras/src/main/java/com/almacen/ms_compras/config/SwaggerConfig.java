package com.almacen.ms_compras.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI comprasOpenAPI() {

        Server localServer = new Server();
        // Nota: Asegúrate de mapear el puerto real de tu ms-compras si no es el 8082
        localServer.setUrl("http://localhost:8088");
        localServer.setDescription("Servidor Local - Microservicio de Compras");

        Info info = new Info()
                .title("API de Microservicio de Compras (ms-compras)")
                .version("1.0.0")
                .description("Este microservicio se encarga de registrar, procesar y listar las compras y órdenes generadas dentro del sistema de Almacén.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}