package com.almacen.ms_productos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.almacen.ms_productos.dto.CategoriaDTO;

@FeignClient(name = "ms-categoria")
public interface CategoriaClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO obtenerPorId(@PathVariable("id") Long id);

}