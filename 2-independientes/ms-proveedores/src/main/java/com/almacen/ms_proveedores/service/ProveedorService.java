package com.almacen.ms_proveedores.service;



import java.util.List;

import com.almacen.ms_proveedores.dto.ProveedorRequestDTO;
import com.almacen.ms_proveedores.dto.ProveedorResponseDTO;

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
}