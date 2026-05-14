package com.almacen.ms_productos.service;

import com.almacen.ms_productos.dto.*;

import java.util.List;

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