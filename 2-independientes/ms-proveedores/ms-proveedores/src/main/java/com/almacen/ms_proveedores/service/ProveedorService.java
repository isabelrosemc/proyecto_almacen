package com.almacen.ms_proveedores.service;

import com.ms.proveedores.dto.*;

import java.util.List;

public interface ProveedorService {

    ProveedorResponseDTO crearProveedor(
            ProveedorRequestDTO request
    );

    List<ProveedorResponseDTO> listarProveedores();

    ProveedorResponseDTO buscarPorId(Long id);

    ProveedorResponseDTO actualizarProveedor(
            Long id,
            ProveedorRequestDTO request
    );

    void eliminarProveedor(Long id);
}}