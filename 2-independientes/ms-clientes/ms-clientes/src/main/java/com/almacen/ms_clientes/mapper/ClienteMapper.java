package com.almacen.ms_clientes.mapper;

import com.almacen.ms_clientes.dto.ClienteRequestDTO;
import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.model.Cliente;

import java.time.LocalDateTime;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {

        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .estado(dto.getEstado())
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    public static ClienteResponseDTO toDTO(Cliente cliente) {

        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nombreCompleto(cliente.getNombre() + " " + cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .estado(cliente.getEstado())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }
}