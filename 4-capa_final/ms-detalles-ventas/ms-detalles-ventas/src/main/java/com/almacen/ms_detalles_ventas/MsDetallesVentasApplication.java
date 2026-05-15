package com.almacen.ms_detalles_ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDetallesVentasApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                MsDetallesVentasApplication.class,
                args
        );
    }
}