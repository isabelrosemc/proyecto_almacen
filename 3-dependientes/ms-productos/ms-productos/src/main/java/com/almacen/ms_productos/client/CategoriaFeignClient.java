package com.almacen.ms_productos.client;

import com.almacen.ms_productos.dto.CategoriaDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-categorias")
public interface CategoriaFeignClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO obtenerCategoria(
            @PathVariable Long id
    );
}