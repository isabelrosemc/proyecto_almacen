package com.almacen.ms_productos.service;


import java.util.List;

import com.almacen.ms_productos.dto.ProductoRequestDTO;
import com.almacen.ms_productos.dto.ProductoResponseDTO;

public interface ProductoService {

    ProductoResponseDTO crearProducto(
            ProductoRequestDTO request
    );

    List<ProductoResponseDTO> listarProductos();

    ProductoResponseDTO buscarPorId(Long id);

    ProductoResponseDTO actualizarProducto(
            Long id,
            ProductoRequestDTO request
    );

    void eliminarProducto(Long id);
}