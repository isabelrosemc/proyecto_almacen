package com.almacen.ms_categoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCategoriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCategoriaApplication.class, args);
    }
}