package com.almacen.ms_productos.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_productos.dto.CategoriaDTO;

@FeignClient(name = "ms-categorias")
public interface CategoriaClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO obtenerCategoria(
            @PathVariable Long id
    );
}